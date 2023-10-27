import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Simulation {
	
	/**
	 * aktualni radka v souboru
	 */
	static String line;
	/**
	 * reader pro soubor
	 */
	static BufferedReader file;
	/**
	 * pole vrcholu grafu
	 */
	static Vertex[] vertexes;
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
	static Path[] paths;
	/**
	 * pole druhu kolecek
	 */
	static Wheelbarrow[] wheelTypes;
	/**
	 * pole pozadavku
	 */
	static Request[] requests;
	/**
	 * vazena matice vzdalenosti reprezentujici graf
	 */
	static double wMatrix[][];
	/**
	 * pole predchudcu vrcholu v nejkratsi ceste
	 */
	static int predchudce[][];
	/**
	 * hodnota reprezentujici nekonecno ve vazene matici grafu
	 */
	private static final int INFINITY = Integer.MAX_VALUE;

	
	/**
	 * Hlavni metoda spoustejici program
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		Input input = new Input();
		input.read();
		//GUI gui = new GUI(input);
		
		/**
		 * Vytvoreni objektu z vstupnich dat
		 */
		createObjects(input.getOutput());
		
		System.out.println("Velikosti: W - " + warehouses.length + " | " + "C - " + customers.length + " | " + "P - " + paths.length + " | " + "WHEE - " + wheelTypes.length + " | " + "R - " + requests.length);
		
		predchudce = new int[vertexes.length][vertexes.length];
		for(int i = 0; i < vertexes.length; i++) {
			Arrays.fill(predchudce[i], -1);
		}
		
		FWalgorithm();
	
		
		//Vypis matice pod FWalg
		/*	
			for(int i = 0; i < vertexes.length; i++) {
				for(int j = 0; j < vertexes.length; j++) {
					System.out.print(wMatrix[i][j]+" ");
				}
				System.out.println();
			}
		
		*/
		
		//TODO: sort pole requestu a zarazeni do fronty
		
	}
	
	/**
	 * Metoda vytvarejici objekty ze vstupnich dat a vytvarejici reprezentaci grafu pomoci vazene matice sousednosti
	 * @param processedFile
	 */
	public static void createObjects(String processedFile) {
		try {
			file = Files.newBufferedReader(Paths.get(processedFile));
			createWarehouse(Integer.parseInt(file.readLine()));
			createCustomer(Integer.parseInt(file.readLine()));
			vertexes = new Vertex[warehouses.length + customers.length];
			fillVertexes();
			wMatrix = new double[vertexes.length][vertexes.length];
			for(int i = 0; i < vertexes.length; i++) {
				for(int j = 0; j < vertexes.length; j++) {
					if(i == j) wMatrix[i][j] = 0;
					else wMatrix[i][j] = INFINITY;
				}
			}
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
		for(int i = 0 ; i < warehouses.length; i++) {
			vertexes[i] = warehouses[i];
			j++;
		}
		for(int i = 0 ; i < customers.length; i++) {
			vertexes[j] = customers[i];
			j++;
		}
	}

	/**
	 * Metoda vytvarejici sklady
	 * @param count
	 */
	public static void createWarehouse(int count) {
		warehouses = new Warehouse[count];
		for(int i = 0; i < count; i++) {
			try {
			warehouses[i] = new Warehouse(
					Double.parseDouble( file.readLine()),
					Double.parseDouble(file.readLine()),
					Integer.parseInt( file.readLine()),
					Double.parseDouble( file.readLine()),
					Double.parseDouble(file.readLine())
					);
			System.out.println("W");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Metoda vytvarejici zakazniky
	 * @param count
	 */
	public static void createCustomer(int count) {
		customers = new Customer[count];
		for(int i = 0; i < count; i++) {
			try {
			customers[i] = new Customer(
					Double.parseDouble( file.readLine()),
					Double.parseDouble( file.readLine())
					);
			System.out.println("C");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metoda vytvarejici cesty a vypocitavajici ohodnoceni cesty
	 * @param count
	 */
	public static void createPath(int count) {
		paths = new Path[count];
		for(int i = 0; i < count; i++) {
			try {
				int x = Integer.parseInt( file.readLine());
				int y = Integer.parseInt( file.readLine());
				paths[i] = new Path(
									x,
									y
								);
				paths[i].calculateDistance(vertexes[x-1].getX(), vertexes[x-1].getY(), vertexes[y-1].getX(), vertexes[y-1].getY());
				wMatrix[x-1][y-1] = paths[i].getDistance();
				wMatrix[y-1][x-1] = wMatrix[x-1][y-1];
				System.out.println("P");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metoda vytvarejici druhy kolecek
	 * @param count
	 */
	public static void createWheelbarrow(int count) {
		wheelTypes = new Wheelbarrow[count];
		for(int i = 0; i < count; i++) {
			try {
			wheelTypes[i] = new Wheelbarrow(
					file.readLine(),
					Double.parseDouble( file.readLine()),
					Double.parseDouble( file.readLine()),
					Double.parseDouble( file.readLine()),
					Double.parseDouble( file.readLine()),
					Double.parseDouble( file.readLine()),
					Integer.parseInt( file.readLine()),
					Double.parseDouble( file.readLine())
					);
			System.out.println("K");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Metoda vytvarejici pozadavky
	 * @param count
	 */
	public static void createRequest(int count) {
		requests = new Request[count];
		for(int i = 0; i < count; i++) {
			try {
			requests[i] = new Request(
					Double.parseDouble( file.readLine()),
					Integer.parseInt( file.readLine()),
					Integer.parseInt( file.readLine()),
					Double.parseDouble( file.readLine())
					);
			System.out.println("R");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Metoda provadejici upravu matice pomoci Floyd-Warshallovy metody pro zjisteni vzdalenosti v grafu
	 */
	public static void FWalgorithm() {
		for (int k = 0; k < vertexes.length; k++) {
            for (int i = 0; i < vertexes.length; i++) {
                for (int j = 0; j < vertexes.length; j++) {
                    if ((wMatrix[i][k] != INFINITY) && (wMatrix[k][j] != INFINITY) && (wMatrix[i][k] + wMatrix[k][j] < wMatrix[i][j])) {
                    	wMatrix[i][j] = wMatrix[i][k] + wMatrix[k][j];
                        predchudce[i][j] = k;
                    }
                }
            }
        }
	}
	
}
