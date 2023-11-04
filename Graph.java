import java.util.LinkedList;

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
	      neighbours[source].add(new Edge(destination, distance));
	  }
}
