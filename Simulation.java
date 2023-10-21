import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Simulation {
	
	static String line;
	static BufferedReader file;
	static Vertex[] vertexes;
	static Warehouse[] warehouses;
	static Customer[] customers;
	static Path[] paths;
	static Wheelbarrow[] wheelTypes;
	static Request[] requests;
	static double wMatrix[][];
	static int predchudce[][];

	public static void main(String[] args) throws Exception {
		
		Input input = new Input();
		input.read();
		//GUI gui = new GUI(input);
		createObjects(input.getOutput());
		
		System.out.println("Velikosti: W - " + warehouses.length + " | " + "C - " + customers.length + " | " + "P - " + paths.length + " | " + "WHEE - " + wheelTypes.length + " | " + "R - " + requests.length);
	//	System.out.println(customers[1].getX() + " " +customers[1].getY());
	//	for(int i = 0; i < paths.length ; i++) {
	//		System.out.println(paths[i].getDistance()); 
	//	}
		
		predchudce = new int[vertexes.length][vertexes.length];
		for(int i = 0; i < vertexes.length; i++) {
			Arrays.fill(predchudce[i], -1);
		}
		FWalgorithm();
		
		//TODO: sort pole requestu a zarazeni do fronty
		
	}
	
	public static void createObjects(String processedFile) {
		try {
			file = Files.newBufferedReader(Paths.get(processedFile));
			createWarehouse(Integer.parseInt(file.readLine()));
			createCustomer(Integer.parseInt(file.readLine()));
			vertexes = new Vertex[warehouses.length + customers.length];
			wMatrix = new double[vertexes.length][vertexes.length];
			for(int i = 0; i < vertexes.length; i++) {
				Arrays.fill(wMatrix[i], 0);
			}
			fillVertexes();
			createPath(Integer.parseInt(file.readLine()));
			createWheelbarrow(Integer.parseInt(file.readLine()));
			createRequest(Integer.parseInt(file.readLine()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			System.out.println("P");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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

	public static void FWalgorithm() {
		for (int k = 0; k < vertexes.length; k++) {
            for (int i = 0; i < vertexes.length; i++) {
                for (int j = 0; j < vertexes.length; j++) {
                    if (wMatrix[i][k] != 0 && wMatrix[k][j] != 0 && wMatrix[i][k] + wMatrix[k][j] < wMatrix[i][j]) {
                    	wMatrix[i][j] = wMatrix[i][k] + wMatrix[k][j];
                        predchudce[i][j] = k;
                    }
                }
            }
        }
	}
	
}
