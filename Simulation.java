import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

/**
 * Trida pravadejici simulaci
 * @author Tomas Rychetsky / adam mika
 *
 */
public class Simulation {

	/**
	 * aktualni radka v souboru
	 */
	static String line;
	/**
	 * index pozadavku
	 */
	static int indexP = 0;
	/**
	 * reader pro soubor
	 */
	static BufferedReader file;
	/**
	 * pole vrcholu grafu
	 */
	static Vertex[] vertices;
	/**
	 * pole skladu
	 */
	static Warehouse[] warehouses;
	/**
	 * pole zakazniku
	 */
	static Customer[] customers;
	/**
	 * pole druhu kolecek
	 */
	static Wheelbarrow[] wheelTypes;
	/**
	 * aktualni kolecko
	 */
	static Wheelbarrow wheel;
	/**
	 * pole pozadavku
	 */
	static Request[] requests;
	/**
	 * objekt reprezentujici graf ulohy
	 */
	private static Graph graph;
	/**
	 * pole vzdalenosti s aktualne kontrolovaneho bodu
	 */
	private static double[] distances;
	/**
	 * pole arraylistu obsahujici cestu z aktualniho bodu do vsech ostatnich
	 */
	private static ArrayList<Integer>[] pathsFrom;
	/**
	 * ID skladu odkud se prevazi aktualne kolecko
	 */
	private static int spWarehouseID;
	/**
	 * aktualni cas simulace
	 */
	private static double time = 0;
	/**
	 * celkovy pocet dorucenych pytlu
	 */
	private static int totalBags = 0;
	/**
	 * celkovy pocet splnenych pozadavku
	 */
	private static int totalSRequests = 0;
	/**
	 * Fronta pozadavku
	 */
	private static PriorityQueue<Request> requestQ;

	
	/**
	 * Hlavni metoda spoustejici program
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args){

		Input input = new Input();
		input.setInput("data/middleS.txt");
		input.read();

		// Vytvoreni objektu z vstupnich dat	
		createObjects(input.getOutput());

		
		//Zacatek simulace 
		if(requestQ.isEmpty()) {
			System.out.println("Nejsou zadne pozadavky");
		}

		//long start = System.currentTimeMillis();

		startRequest();

		stats();
		//long stop = System.currentTimeMillis();
		//isCervenkaHappy(start, stop);
		
	}

	/**
	 * Rekurzivní metoda startujici simulaci a plneni pozadavku v prioritni fronte. Pokud pozadavek
	 * cestuje odstartuje se další
	 * */
	public static void startRequest(){
		while (!requestQ.isEmpty()) {
			Request current = requestQ.poll();
			arrivedRequest(current);

			if (wheelVerification(current)) {
				if(!travelling(current)) {
					break;
				}
				if (!requestQ.isEmpty()) {
					//current = requestQ.poll();
					//arrivedRequest(current);
				} else {
					break; // Nejsou requesty
				}
			}
			else {
				//umrznul zakaznik
				break;
			}
		}
	}

	/**
	 * Metoda vytvarejici objekty ze vstupnich dat a vytvarejici reprezentaci grafu
	 * pomoci vazene matice sousednosti
	 * 
	 * @param processedFile vstupni soubor s odstranenymi kometary
	 */
	public static void createObjects(String processedFile) {
		try {
			file = Files.newBufferedReader(Paths.get(processedFile));
			createWarehouse(Integer.parseInt(file.readLine()));
			createCustomer(Integer.parseInt(file.readLine()));
			vertices = new Vertex[warehouses.length + customers.length];
			graph = new Graph(vertices.length+1);
			fillVertexes();
			createPath(Integer.parseInt(file.readLine()));
			createWheelbarrow(Integer.parseInt(file.readLine()));
			createRequest(Integer.parseInt(file.readLine()));
			
			graph = graph.primMST(graph);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metoda plnici pole vrcholu grafu pomoci pole skladu a pole zakazniku
	 */
	public static void fillVertexes() {
		int j = 0;
		for (int i = 0; i < warehouses.length; i++) {
			vertices[i] = warehouses[i];
			j++;
		}
		for (int i = 0; i < customers.length; i++) {
			vertices[j] = customers[i];
			j++;
		}
	}

	/**
	 * Metoda vytvarejici sklady
	 * 
	 * @param count pocet skladu
	 */
	public static void createWarehouse(int count) {
		warehouses = new Warehouse[count];
		for (int i = 0; i < count; i++) {
			try {
				warehouses[i] = new Warehouse(
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()),
						Integer.parseInt(file.readLine()),
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()));
			}

			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metoda vytvarejici zakazniky
	 * 
	 * @param count pocet zakazniku
	 */
	public static void createCustomer(int count) {
		customers = new Customer[count];
		for (int i = 0; i < count; i++) {
			try {
				customers[i] = new Customer(
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metoda vytvarejici cesty a vypocitavajici ohodnoceni cesty
	 * 
	 * @param count pocet cest
	 */
	public static void createPath(int count) {
		for (int i = 0; i < count; i++) {
			try {
				int id1 = Integer.parseInt(file.readLine());
				int id2 = Integer.parseInt(file.readLine());
				double x = vertices[id1 - 1].getX() - vertices[id2 - 1].getX();
				double y = vertices[id1 - 1].getY() - vertices[id2 - 1].getY();
				double distance = Math.sqrt( (x*x) + (y*y) );
				graph.addEdge(id1, id2, distance);
				graph.addEdge(id2, id1, distance);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metoda vytvarejici druhy kolecek
	 * 
	 * @param count pocet druhu kolecek
	 */
	public static void createWheelbarrow(int count) {
		wheelTypes = new Wheelbarrow[count];
		for (int i = 0; i < count; i++) {
			try {
				wheelTypes[i] = new Wheelbarrow(
						file.readLine(),
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()),
						Double.parseDouble(file.readLine()),
						Integer.parseInt(file.readLine()),
						Double.parseDouble(file.readLine()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metoda vytvarejici pozadavky
	 * 
	 * @param count pocet pozadavku
	 */
	public static void createRequest(int count) {
		requests = new Request[count];
		requestQ = new PriorityQueue<>(requests.length, Comparator.comparingDouble(request -> request.getTz()));
		for (int i = 0; i < count; i++) {
			try {
				requests[i] = new Request(
						Double.parseDouble(file.readLine()),
						Integer.parseInt(file.readLine()),
						Integer.parseInt(file.readLine()),
						Double.parseDouble(file.readLine()));
				requestQ.add(requests[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count == 0) {
			requests = new Request[1];
		}
	}


	/**
	 * Metoda vypisujici prichozi pozadavek
	 * @param request aktualni pozadavek
	 */
	public static void arrivedRequest (Request request) {
		int thisIndex = indexP + 1;
		double deadline = request.getTp() + request.getTz();
	//	time += request.getTz();
		System.out.println("Cas: "+ (int)time +", Pozadavek: "+ thisIndex +", Zakaznik: "+ request.getID() +", Pocet pytlu: "+ request.getN() +", Deadline: "+ deadline);
	}

	/**
	 * Metoda generuji kolecko podle pravdepodobnosti typu kolecek
	 * @param types druhy kolecek
	 * @return vraci vybrany typ kolecka
	 */
	public static Wheelbarrow getWheelType (Wheelbarrow[] types){
		Random random = new Random();
		double randomWheel = random.nextDouble();
		double typesSum = 0;
		for (Wheelbarrow type : types){
			typesSum += type.getProbability();
			if (typesSum >= randomWheel){
				return type;
			}
		}
		return null;
	}

	/**
	 * Metoda vytvarejici koleckoa overujici ze je schopne splnit aktualni pozadavek
	 * @param newRequest aktualni pozadavek
	 * @return vraci true pokud je kolecko vytvoreno, jinak false
	 */
	public static boolean wheelVerification(Request newRequest){

		//ziskani druhu kolecka
		Wheelbarrow thisWheelType = getWheelType(wheelTypes);

		if (thisWheelType == null){
			System.out.println("No wheel types");
			return false;
		}
		wheel = new Wheelbarrow(thisWheelType);
		
		//urceni vzdalenosti
		distances = dijkstra(graph, warehouses.length+newRequest.getID());
		
		//
		double spToWarehouse = Double.MAX_VALUE;
		spWarehouseID = -1;
		
		double nearestSP = Double.MAX_VALUE;
		int nearestWareID = -1;
		
		int badWarehouse = 0;
		
		//urceni nejvhodnejsiho skladu
		for(int i = 0; i < distances.length; i++) {
			if(i <= warehouses.length) {
				if(distances[i] < spToWarehouse) {
					if((int)time != 0) {
						//Doplnovani skladu
						int restock = warehouses[i-1].getKs();
						double lastR = time-warehouses[i-1].getLastTs();
						int mul = ((int)(lastR/warehouses[i-1].getTs()));
						warehouses[i-1].setBc(warehouses[i-1].getBc()+(mul*restock));
						warehouses[i-1].setLastTs(time);
					}
					if(warehouses[i-1].getBc() >= newRequest.getN()) {
						spToWarehouse = distances[i];
						spWarehouseID = i;
					}
					nearestWareID = i;
					nearestSP = distances[i];
					
					if(warehouses[i-1].getKs() <= 0) {
						 badWarehouse++;
					}
				}
			}
		}
		if(spWarehouseID == -1 && badWarehouse == warehouses.length) {
			System.out.println("Zadny sklad nema dostatek pytlu pro obslouzeni zakaznika");
			System.out.println("Cas: "+(int)newRequest.getTp()+", Zakaznik "+newRequest.getID()+" umrzl zimou, protoze jezdit s koleckem je hloupost, konec");
			return false;
		}
		if(spWarehouseID == -1) {
			//TODO cekat dokud neni dost
			time = time + warehouses[nearestWareID-1].getTs();
			warehouses[nearestWareID-1].setBc(warehouses[nearestWareID-1].getBc()+warehouses[nearestWareID-1].getKs());
			warehouses[nearestWareID-1].setLastTs(time);
			spWarehouseID = nearestWareID;
			spToWarehouse = nearestSP;
		}
		/////////////////////////////////////////////////////////////////////

		double customerDistance = spToWarehouse * 2;
		double deadline = newRequest.getTz() + newRequest.getTp();
		double wheelTime = calculateTime(spToWarehouse, wheel.getVelocity(), spWarehouseID-1);
		
		
		//Kontrola druhu kolecek
		int wrCount = 0;
		for(int i = 0; i <wheelTypes.length; i ++) {
			if(wheelTypes[i].getDMax() < (customerDistance/2)) {
				wrCount++;
			}
		}
		if(wrCount == wheelTypes.length) {
			System.out.println("Zadny druh kolecka nema dostatecnou maximalni vzdalenost pro dosazeni zakaznika");
			System.out.println("Cas: "+(int)newRequest.getTp()+", Zakaznik "+newRequest.getID()+" umrzl zimou, protoze jezdit s koleckem je hloupost, konec");
			return false;
		}
		//////////////////
		boolean findWheel = false;

		//TODO vyslani vice kolecek pokud to nezvladne nalozit 1 ----- pravdepodobne spis poslani tam jednoho co se pak vrati a pojede tam znova

		//Vybrani/vytvoreni vhodneho kolecka pro splneni pozadavku
		if(!warehouses[spWarehouseID - 1].emptyWheel()) {

			Stack<Wheelbarrow> copyWheelbarrowStack = warehouses[spWarehouseID - 1].cloneWheel();

			for (Wheelbarrow wheelbarrow : copyWheelbarrowStack) {
				//kontrola jestli je uz opravene
				if(wheelbarrow.getTr() <= time) {
					//pokud jo nastavim
					if(wheelbarrow.getRepairing()) {
						wheelbarrow.setRepairing(false, -1);
						wheelbarrow.setDcurrent(wheelbarrow.getDistance(),true);
					}
					wheelTime = calculateTime(spToWarehouse, wheelbarrow.getVelocity(), spWarehouseID - 1);
					if (wheelbarrow.getDistance() >= customerDistance && wheelbarrow.getVolume() >= newRequest.getN() && wheelTime <= deadline) {
						findWheel = true;
						wheel = wheelbarrow;
						warehouses[spWarehouseID - 1].remWheel(wheelbarrow);
						break;
					}
				}
				else {
					System.out.println("kolecko id: "+wheelbarrow.getID()+" se opravuje");
				}
			}
		} 
		if (!findWheel) {
			while (wheel.getDistance() < customerDistance || wheel.getVolume() < newRequest.getN() || wheelTime >= deadline) {

				thisWheelType = getWheelType(wheelTypes);
				wheel = new Wheelbarrow(thisWheelType);
				warehouses[spWarehouseID - 1].pushWheel(wheel);

				wheelTime = calculateTime(spToWarehouse, wheel.getVelocity(), spWarehouseID - 1);

			}
			if (!warehouses[spWarehouseID - 1].emptyWheel()) {
				warehouses[spWarehouseID - 1].popWheel().printID();
			}
		}
		/////////////////////////////////////////////////////
		
		//Nalozeni pytlu - pokud pozadavek vetsi nez kolecko odecte objem kolecka, jinak odecte objem pozadavku
		if(wheel.getVolume() <= newRequest.getN()) {
			warehouses[spWarehouseID-1].setBc(warehouses[spWarehouseID-1].getBc()-wheel.getVolume());
		}
		else {
			warehouses[spWarehouseID-1].setBc(warehouses[spWarehouseID-1].getBc()-newRequest.getN());
		}
		System.out.println("Pytlu ve skladu po nalozeni: "+warehouses[spWarehouseID-1].getBc());
		
		
		System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Sklad: "+spWarehouseID+", Nalozeno pytlu: "+wheel.getVolume()+", Odjezd v: "+(int)(time+warehouses[spWarehouseID-1].getTn()));
		
		return true;
		
		}

	/**
	 * Metoda pocitajici cas potrebny k doruceni
	 * @param distance vzdalenost do cile
	 * @param velocity rychlost kolecka
	 * @param warehouseID ID skladu odkud kolecko jede
	 * @return vraci vypocteny cas cesty
	 */
	public static double calculateTime(double distance, double velocity, int warehouseID){
		double time = (distance / velocity) + warehouses[warehouseID].getTn();
		return time;
	}
	
	/**
	 * Metoda provadejici vypisy cesty pro dany pozadavek
	 * @param current aktualni pozadavek
	 */
	public static boolean travelling(Request current) {
		
		//odjezd
		time += warehouses[spWarehouseID-1].getTn();
		System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", odjizdi ze Sklad: "+spWarehouseID);
		
		//cesta k zakaznikovi
		double distance = 0;
		//KUK na kolem zakazniky
		for(int i = 1; i < pathsFrom[current.getID()].size(); i++) {
			Vertex next = vertices[pathsFrom[current.getID()].get(i)-1];
			if(next instanceof Customer) {
				distance = dijkstra(graph, spWarehouseID, pathsFrom[current.getID()].get(i));
				time += distance / wheel.getVelocity();
				System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Zakaznik: "+pathsFrom[current.getID()].get(i)+", Kuk na "+wheel.name+" kolecko");
			}
		}
		//
		time += (distances[spWarehouseID] - distance) / wheel.getVelocity();
		//kontrola ze dojel vcas
		if((current.getTp()-time) < 0) {
			System.out.println("Cas: "+(int)time+", Zakaznik "+current.getID()+" umrzl zimou, protoze jezdit s koleckem je hloupost, konec");
			return false;
		}
		//doruceni
		System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Zakaznik: "+current.getID()+", Vylozeno pytlu: "+current.getN()+", Vylozeno v: "+(int)time+", Casova rezerva: "+(int)(current.getTp()-time));
		totalBags += current.getN();
		totalSRequests++;
		
		//cesta zpet do skladu
		distance = 0;
		//KUK na kolem zakazniky
		for(int i = pathsFrom[current.getID()].size()-1; i >= 1; i--) {
			Vertex next = vertices[pathsFrom[current.getID()].get(i)-1];
			if(next instanceof Customer) {
				distance = dijkstra(graph, spWarehouseID, pathsFrom[current.getID()].get(i));
				time += distance / wheel.getVelocity();
				System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Zakaznik: "+pathsFrom[current.getID()].get(i)+", Kuk na "+wheel.name+" kolecko");
			}
		}
		//navraceni do skladu a vraceni kolecka do zasobniku
		time += (distances[spWarehouseID] - distance) / wheel.getVelocity();
		warehouses[spWarehouseID - 1].pushWheel(wheel);
		//oprava pokud uz dojede jen 1/3 a mene sveho puvodniho dojezdu
		wheel.setDcurrent((distances[spWarehouseID]*2),false);
		System.out.println(wheel.getDcurrent());
		if(requestQ.peek() != null) {
			int nextCustomer = requestQ.peek().getID();
			if(wheel.getDcurrent() < ((distances[spWarehouseID]+distances[nextCustomer+warehouses.length])*2)) {
				wheel.setRepairing(true, time);
			}
		}
		
		System.out.println("Cas: "+(int)(time)+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Navrat do skladu: "+spWarehouseID);
		return true;
	}
	
	/**
	 * dijkstruv algoritmus pro nalezeni cesty z jednoho bodu do vsech ostatnich
	 * @param graph graf nad kterym je algoritmus provaden
	 * @param startVertex pocatecni vrchol
	 * @return vraci pole vzdalenosti od vstupniho vrcholu
	 */
	public static double[] dijkstra(Graph graph, int startVertex) {
		 int vertices = graph.neighbours.length;

	        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertices, Comparator.comparingDouble(edge -> edge.distance));
	        distances = new double[vertices];
	        pathsFrom = new ArrayList[vertices];

	        for (int i = 0; i < vertices; i++) {
	            distances[i] = Double.POSITIVE_INFINITY;
	            pathsFrom[i] = new ArrayList<>();
	        }

	        distances[startVertex] = 0;
	        priorityQueue.add(new Edge(startVertex, 0, -1)); 

	        while (!priorityQueue.isEmpty()) {
	            Edge currentEdge = priorityQueue.poll();
	            int start = currentEdge.dest;

	            for (Edge neighbour : graph.neighbours[start]) {
	                int next = neighbour.dest;
	                double weight = neighbour.distance;

	                double newDistance = distances[start] + weight;
	                if (newDistance < distances[next]) {
	                    distances[next] = newDistance;

	                    pathsFrom[next].clear();
	                    pathsFrom[next].addAll(pathsFrom[start]);
	                    pathsFrom[next].add(start);

	                    priorityQueue.add(new Edge(next, newDistance, start));
	                }
	            }
	        }

	        return distances;
    
    }
	
	/**
	 * dijkstruv algoritmus pro nalezeni cesty z jednoho bodu do druheho
	 * @param graph graf nad kterym je algoritmus provaden
	 * @param startVertex pocatecni vrchol
	 * @param endVertex koncovi vrchol
	 * @return vraci vzdalenost vrcholu v grafu
	 */
	public static double dijkstra(Graph graph, int startVertex, int endVertex) {
        int vertices = graph.neighbours.length;
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertices, Comparator.comparingDouble(edge -> edge.distance));
        double[] distances = new double[vertices];

        for (int i = 0; i < vertices; i++) {
            distances[i] = Double.MAX_VALUE;
        }

        distances[startVertex] = 0;
        priorityQueue.add(graph.neighbours[startVertex].getFirst());

        while (!priorityQueue.isEmpty()) {
            int u = priorityQueue.poll().dest;

            if (u == endVertex) {
                return distances[u];
            }

            for (int k = 0; k < graph.neighbours[u].size(); k++) {
                Edge neighbour = graph.neighbours[u].get(k);
                int v = neighbour.dest;
                double weight = neighbour.distance;

                double newDistance = distances[u] + weight;
                if (newDistance < distances[v]) {
                    distances[v] = newDistance;
                    priorityQueue.add(new Edge(v, newDistance, u));
                }
            }
        }

        return -1;
    }
	
	/**
	 * Metoda vypisujici statistiku na konci programu
	 */
	public static void stats() {
		System.out.println("Celkem obslouzeno pozadavku: "+totalSRequests+" a doruceno celkem "+totalBags+" pytlu.");
	}
	
	/**
	 * Metoda provadejici prevedeni grafu na minimalni kostru pomoci Prim-Jranikova algoritmu
	 * @param graph vstupni graf
	 * @return graf minimalni kostry vstupniho grafu
	 */
/*	public static Graph primMST(Graph graph) {
        int vertices = graph.neighbours.length;
        Graph minimumSpanningTreeGraph = new Graph(vertices);

        // Priority fronta pro uchování hran s jejich vahou
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertices, (edge1, edge2) -> Double.compare(edge1.distance, edge2.distance));

        // Přidání prvního vrcholu do priority fronty
        priorityQueue.add(graph.neighbours[1].getFirst());

        // Pole pro sledování, zda byl vrchol již přidán do minimální kostry
        boolean[] inMinimumSpanningTree = new boolean[vertices];

        while (!priorityQueue.isEmpty()) {
            // Vybrání hrany s nejmenší vahou
            Edge currentEdge = priorityQueue.poll();
            int currentVertex = currentEdge.dest;

            // Přidání hrany do minimální kostry, pokud vrchol ještě není v ní obsažen
            if (!inMinimumSpanningTree[currentVertex]) {
                minimumSpanningTreeGraph.addEdge(currentEdge.source, currentEdge.dest, currentEdge.distance);
                minimumSpanningTreeGraph.addEdge(currentEdge.dest, currentEdge.source, currentEdge.distance);
                inMinimumSpanningTree[currentVertex] = true;
                //inMinimumSpanningTree[currentEdge.source] = true;

                // Přidání sousedních hran do priority fronty
                for (Edge neighbor : graph.neighbours[currentVertex]) {
                    if (!inMinimumSpanningTree[neighbor.dest]) {
                        priorityQueue.add(neighbor);
                    }
                }
            }
        }

        return minimumSpanningTreeGraph;
    }
*/
	/**
	 * Metoda vypisujici informaci o spokojenosti Cervenky
	 */
/*	public static void isCervenkaHappy(long start, long stop){
		long time = (stop - start) / 60000;
		if (time > 20) {
			System.out.println("Cervenka je smutny, protoze program bezi "+time+" minut\n" + "_$$$$$__ __$$$___ $$$$$___ ____$$$$_ $$$$$$$_ $$$$$$__ $$___$$_ $$$$$$$_ $$___$$_ $$___$$_ __$$$___\n" +
																								"$$___$$_ _$$_$$__ $$__$$__ ___$$____ $$______ $$___$$_ $$___$$_ $$______ $$$__$$_ $$__$$__ _$$_$$__\n" +
																								"_$$$____ $$___$$_ $$___$$_ __$$_____ $$$$$___ $$___$$_ _$$_$$__ $$$$$___ $$$$_$$_ $$$$$___ $$___$$_\n" +
																								"___$$$__ $$$$$$$_ $$___$$_ __$$_____ $$______ $$$$$$__ _$$_$$__ $$______ $$_$$$$_ $$__$$__ $$$$$$$_\n" +
																								"$$___$$_ $$___$$_ $$__$$__ ___$$____ $$______ $$___$$_ __$$$___ $$______ $$__$$$_ $$___$$_ $$___$$_\n" +
																								"_$$$$$__ $$___$$_ $$$$$___ ____$$$$_ $$$$$$$_ $$___$$_ ___$____ $$$$$$$_ $$___$$_ $$___$$_ $$___$$_");}
		else {
			System.out.println("Cervenka je stastny, protoze program bezi jen "+time+" minut a neco drobneho\n" + "$$___$$_ __$$$___ $$$$$$__ $$$$$$__ $$____$$_ ____$$$$_ $$$$$$$_ $$$$$$__ $$___$$_ $$$$$$$_ $$___$$_ $$___$$_ __$$$___\n" +
																									"$$___$$_ _$$_$$__ $$___$$_ $$___$$_ _$$__$$__ ___$$____ $$______ $$___$$_ $$___$$_ $$______ $$$__$$_ $$__$$__ _$$_$$__\n" +
																									"$$$$$$$_ $$___$$_ $$___$$_ $$___$$_ __$$$$___ __$$_____ $$$$$___ $$___$$_ _$$_$$__ $$$$$___ $$$$_$$_ $$$$$___ $$___$$_\n" +
																									"$$___$$_ $$$$$$$_ $$$$$$__ $$$$$$__ ___$$____ __$$_____ $$______ $$$$$$__ _$$_$$__ $$______ $$_$$$$_ $$__$$__ $$$$$$$_\n" +
																									"$$___$$_ $$___$$_ $$______ $$______ ___$$____ ___$$____ $$______ $$___$$_ __$$$___ $$______ $$__$$$_ $$___$$_ $$___$$_\n" +
																									"$$___$$_ $$___$$_ $$______ $$______ ___$$____ ____$$$$_ $$$$$$$_ $$___$$_ ___$____ $$$$$$$_ $$___$$_ $$___$$_ $$___$$_");}
	}
*/
}
