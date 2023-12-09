package pt;
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
    /**
     * startovni vrchol
     */
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

    /**
     * Getter na cilovy vrchol
     * @return cilovy vrchol
     */
	public int getDest() {
		return dest;
	}
	/**
	 * Getter na vzdalenost vrcholu
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	
	 /**
     * Getter na startovaci vrchol
     * @return startovaci vrchol
     */
	public int getsource() {
		return source;
	}
    
}
