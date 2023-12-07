package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Trida reprezentujici graf
 * @author TR
 *
 */
public class Graph {
	/**
	 * List vsech pripojenych vrcholu
	 */
	List<Edge>[] neighbours;
	/**
	 * pole arraylistu obsahujici cestu z aktualniho bodu do vsech ostatnich
	 */
	public List<Integer>[] pathsFrom;

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
		 * Metoda provadejici prevedeni grafu na minimalni kostru pomoci Prim-Jranikova algoritmu
		 * @param graph vstupni graf
		 * @return graf minimalni kostry vstupniho grafu
		 */
		public Graph primMST(Graph graph) {
	        int vertices = graph.neighbours.length;
	        if(vertices == 1) {
	        	return graph;
	        }
	        Graph graphMST = new Graph(vertices);

	        //prioritni fronta na hrany
	        Queue<Edge> priorityQueue = new PriorityQueue<>(vertices, (edge1, edge2) -> Double.compare(edge1.distance, edge2.distance));

	        //pridani prvniho vrcholu do prioritni frnoty
	        priorityQueue.add(graph.neighbours[1].get(0));

	        //pole pro zjisteni zda bul uz vrchol pridany
	        boolean[] inMST = new boolean[vertices];

	        while (!priorityQueue.isEmpty()) {
	            
	            Edge currentEdge = priorityQueue.poll();
	            int currentVertex = currentEdge.dest;

	            //pridani hrany pokud tam jeste neni
	            if (!inMST[currentVertex]) {
	            	graphMST.addEdge(currentEdge.source, currentEdge.dest, currentEdge.distance);
	            	graphMST.addEdge(currentEdge.dest, currentEdge.source, currentEdge.distance);
	                inMST[currentVertex] = true;
	                //inMinimumSpanningTree[currentEdge.source] = true;

	                //pridani sousednich hran do fronty
	                for (Edge neighbor : graph.neighbours[currentVertex]) {
	                    if (!inMST[neighbor.dest]) {
	                        priorityQueue.add(neighbor);
	                    }
	                }
	            }
	        }

	        return graphMST;
	    }
		
		/**
		 * dijkstruv algoritmus pro nalezeni cesty z jednoho bodu do druheho
		 * @param graph graf nad kterym je algoritmus provaden
		 * @param startVertex pocatecni vrchol
		 * @param endVertex koncovi vrchol
		 * @return vraci vzdalenost vrcholu v grafu
		 */
		public double dijkstra(Graph graph, int startVertex, int endVertex) {
	        int vertices = graph.neighbours.length;
	        Queue<Edge> priorityQueue = new PriorityQueue<>(vertices, Comparator.comparingDouble(edge -> edge.distance));
	        double[] distances = new double[vertices];

	        for (int i = 0; i < vertices; i++) {
	            distances[i] = Double.MAX_VALUE;
	        }

	        distances[startVertex] = 0;
	        priorityQueue.add(graph.neighbours[startVertex].get(0));

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
		 * dijkstruv algoritmus pro nalezeni cesty z jednoho bodu do vsech ostatnich
		 * @param graph graf nad kterym je algoritmus provaden
		 * @param startVertex pocatecni vrchol
		 * @return vraci pole vzdalenosti od vstupniho vrcholu
		 */
		public double[] dijkstra(Graph graph, int startVertex) {
			 int vertices = graph.neighbours.length;

		        Queue<Edge> priorityQueue = new PriorityQueue<>(vertices, Comparator.comparingDouble(edge -> edge.distance));
		        double[] distances = new double[vertices];
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
}
