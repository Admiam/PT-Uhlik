
public class Warehouse extends Vertex {
	
	/**
	 * kartezske souradnic kazdeho skladu x
	 */
	double x;
	/**
	 * kartezske souradnic kazdeho skladu y
	 */
	double y;
	/**
	 * pocet pytlu ks, ktere jsou do skladu vzdy po uplynuti doby ts doplneny. Na zaćatku simulace
	 */
	int ks;
	/**
	 * doba doplneni zasob
	 */
	double ts;
	/**
	 * doba nalozeni pytlu
	 */
	double tn;
	
}
