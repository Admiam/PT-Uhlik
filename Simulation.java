import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Simulation {
	
	static String line;
	static BufferedReader file;
	static Vertex[] vertexes;
	static Warehouse[] warehouses;
	static Customer[] customers;
	static Path[] paths;
	static Wheelbarrow[] wheels;
	static Request[] requests;

	public static void main(String[] args) throws Exception {
		Input input = new Input();
		input.read();
		//GUI gui = new GUI(input);
		createObjects(input.getOutput());
	}
	
	public static void createObjects(String processedFile) {
		try {
			file = Files.newBufferedReader(Paths.get(processedFile));
			//line = file.readLine();
			createWarehouse(Integer.parseInt(file.readLine()));
			createCustomer(Integer.parseInt(file.readLine()));
			vertexes = new Vertex[warehouses.length + customers.length];
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
			paths[i] = new Path(
					Integer.parseInt( file.readLine()),
					Integer.parseInt( file.readLine())
					);
			System.out.println("P");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void createWheelbarrow(int count) {
		wheels = new Wheelbarrow[count];
		for(int i = 0; i < count; i++) {
			try {
			wheels[i] = new Wheelbarrow(
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

}
