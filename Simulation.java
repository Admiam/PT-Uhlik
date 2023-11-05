import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Trida pravadejici simulaci
 * @author TR
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
	 * pole cest
	 */
//	static Path[] paths;
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
	private static double time;
	/**
	 * celkovy pocet dorucenych pytlu
	 */
	private static int totalBags = 0;
	/**
	 * celkovy pocet splnenych pozadavku
	 */
	private static int totalSRequests = 0;

	/**
	 * Hlavni metoda spoustejici program
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Input input = new Input();
		input.setInput("data/middleM.txt");
		input.read();

		/*
		 * Vytvoreni objektu z vstupnich dat
		 */
		createObjects(input.getOutput());

		if (requests != null && requests[indexP] != null) {
				arrivedRequest(requests[indexP]);
				
				if (wheelVerification(requests[indexP])) {
	
					travelling(requests[indexP]);
					
					stats();
				}	
		}
		else {
			System.out.println("Nejsou zadne pozadavky");
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
		for (int i = 0; i < count; i++) {
			try {
				requests[i] = new Request(
						Double.parseDouble(file.readLine()),
						Integer.parseInt(file.readLine()),
						Integer.parseInt(file.readLine()),
						Double.parseDouble(file.readLine()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(count == 0) requests = new Request[1];
	}


	/**
	 * Metoda vypisujici prichozi pozadavek
	 * @param request aktualni pozadavek
	 */
	public static void arrivedRequest (Request request) {
		int thisIndex = indexP + 1;
		double deadline = request.getTp() + request.getTz();
		time = request.getTz();
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


		Wheelbarrow thisWheelType = getWheelType(wheelTypes);

		if (thisWheelType == null){
			System.out.println("No wheel types");
			return false;
		}
		wheel = new Wheelbarrow(thisWheelType);
		
		distances = dijkstra(graph, warehouses.length+newRequest.getID());
		
		double spToWarehouse = Double.MAX_VALUE;
		spWarehouseID = -1;
		for(int i = 0; i < distances.length; i++) {
			if(i <= warehouses.length) {
				if(distances[i] < spToWarehouse) {
					spToWarehouse = distances[i];
					spWarehouseID = i;
				}
			}
		}

		double customerDistance = spToWarehouse * 2;
		double deadline = newRequest.getTz() + newRequest.getTp();
		double wheelTime = calculateTime(spToWarehouse, wheel.getVelocity(), spWarehouseID-1);
		
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
		
		int falseC = 0;

		while (wheel.getDistance() < customerDistance || wheel.getVolume() < newRequest.getN() || wheelTime >= deadline ) {		
			
			thisWheelType = getWheelType(wheelTypes);
			wheel = new Wheelbarrow(thisWheelType);
			wheelTime = calculateTime(spToWarehouse, wheel.getVelocity(), spWarehouseID-1);
			
		
		}
		
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
	public static void travelling(Request current) {
		
		//odjezd
		time += warehouses[spWarehouseID-1].getTn();
		System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", odjizdi ze Sklad: "+spWarehouseID);
		
		//cesta k zakaznikovi
		double distance = 0;
		for(int i = 1; i < pathsFrom[current.getID()].size(); i++) {
			Vertex next = vertices[pathsFrom[current.getID()].get(i)-1];
			if(next instanceof Customer) {
				distance = dijkstra(graph, spWarehouseID, pathsFrom[current.getID()].get(i));
				time += distance / wheel.getVelocity();
				System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Zakaznik: "+pathsFrom[current.getID()].get(i)+", Kuk na "+wheel.name+" kolecko");
			}
		}
		time += (distances[spWarehouseID] - distance) / wheel.getVelocity();
		System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Zakaznik: "+current.getID()+", Vylozeno pytlu: "+current.getN()+", Vylozeno v: "+(int)time+", Casova rezerva: "+(int)(current.getTp()-time));
		totalBags += current.getN();
		totalSRequests++;
		
		//cesta zpet do skladu
		distance = 0;
		for(int i = pathsFrom[current.getID()].size()-1; i >= 1; i--) {
			Vertex next = vertices[pathsFrom[current.getID()].get(i)-1];
			if(next instanceof Customer) {
				distance = dijkstra(graph, spWarehouseID, pathsFrom[current.getID()].get(i));
				time += distance / wheel.getVelocity();
				System.out.println("Cas: "+(int)time+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Zakaznik: "+pathsFrom[current.getID()].get(i)+", Kuk na "+wheel.name+" kolecko");
			}
		}
		time += (distances[spWarehouseID] - distance) / wheel.getVelocity();
		System.out.println("Cas: "+(int)(time)+", Kolecko: "+wheel.name+", ID: "+wheel.getID()+", Navrat do skladu: "+spWarehouseID);
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
        double[] distances = new double[vertices];
        pathsFrom = new ArrayList[vertices];

        for (int i = 0; i < vertices; i++) {
            distances[i] = Double.MAX_VALUE;
            pathsFrom[i] = new ArrayList<>();
        }

        distances[startVertex] = 0;
        priorityQueue.add(new Edge(startVertex, 0));

        while (!priorityQueue.isEmpty()) {
            int start = priorityQueue.poll().dest;

            for (int k = 0; k < graph.neighbours[start].size(); k++) {
                Edge neighbour = graph.neighbours[start].get(k);
                int next = neighbour.dest;
                double weight = neighbour.distance;

                double newDistance = distances[start] + weight;
                if (newDistance < distances[next]) {
                    distances[next] = newDistance;

                    pathsFrom[next].clear();
                    pathsFrom[next].addAll(pathsFrom[start]);
                    pathsFrom[next].add(start);

                    priorityQueue.add(new Edge(next, newDistance));
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
        priorityQueue.add(new Edge(startVertex, 0));

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
                    priorityQueue.add(new Edge(v, newDistance));
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
}
