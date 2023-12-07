package src;
/**
 * Metoda reprezentujici hranu v grafu
 * @author TR
 *
 */
public class Edge {
	/**
	 * cilovy vrchol
	 */
	int dest;
	/**
	 * vzdalenost od zdrojoveho vrcholu
	 */
    double distance;
    
    int source;

    /**
     * Konstruktor
     * @param dest cilovy vrchol
     * @param distance vzdalenost od zdrojoveho vrcholu
     */
    public Edge(int dest, double distance, int source) {
        this.dest = dest;
        this.distance = distance;
        this.source = source;
    }

	public int getDest() {
		return dest;
	}

	public double getDistance() {
		return distance;
	}
	
	public int getsource() {
		return source;
	}
    
}
