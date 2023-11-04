
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
     * Konstruktor
     * @param dest cilovy vrchol
     * @param distance vzdalenost od zdrojoveho vrcholu
     */
    public Edge(int dest, double distance) {
        this.dest = dest;
        this.distance = distance;
    }
}
