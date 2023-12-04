import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Trida reprezentujici graf
 * @author TR
 *
 */
public class Graph {
	/**
	 * List vsech pripojenych vrcholu
	 */
	 LinkedList<Edge>[] neighbours;

	 /**
	  * Konstruktor
	  * @param vertices pocet vrcholu
	  */
	  public Graph(int vertices) {
	      neighbours = new LinkedList[vertices];
	      for (int i = 0; i < vertices; i++) {
	          neighbours[i] = new LinkedList<>();
	      }
	  }

	  /**
	   * Metoda na pridani hrany
	   * @param source zdrojovy vrchol
	   * @param destination cilovy vrchol
	   * @param distance vzdalenost
	   */
	  public void addEdge(int source, int destination, double distance) {
	      neighbours[source].add(new Edge(destination, distance, source));
	  }
	  
	  /**
	     * Metoda pro výpis grafu
	     */
	    public void printGraph() {
	        for (int i = 0; i < neighbours.length; i++) {
	            System.out.print("Vrchol " + i + " sousedí s: ");
	            for (Edge edge : neighbours[i]) {
	                System.out.print(edge.dest + "(" + edge.distance + ") ");
	            }
	            System.out.println();
	        }
	    }
	    
	    /**
		 * Metoda provadejici prevedeni grafu na minimalni kostru pomoci Prim-Jranikova algoritmu
		 * @param graph vstupni graf
		 * @return graf minimalni kostry vstupniho grafu
		 */
		public Graph primMST(Graph graph) {
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
}
