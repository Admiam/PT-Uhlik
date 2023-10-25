
public class Warehouse extends Vertex {
	
	/**
	 * kartezske souradnic kazdeho skladu x
	 */
	private double x;
	/**
	 * kartezske souradnic kazdeho skladu y
	 */
	private double y;
	/**
	 * pocet pytlu ks, ktere jsou do skladu vzdy po uplynuti doby ts doplneny. Na zaćatku simulace
	 */
	private int ks;
	/**
	 * doba doplneni zasob
	 */
	private double ts;
	/**
	 * doba nalozeni pytlu
	 */
	private double tn;
	
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 * @param ks
	 * @param ts
	 * @param tn
	 */
	public Warehouse(double x, double y, int ks, double ts, double tn) {
		this.x = x;
		this.y = y;
		this.ks = ks;
		this.ts = ts;
		this.tn = tn;
	}
	
	/**
	 * Getter pro ziskani souradnice X skladu
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter pro ziskani souradnice Y skladu
	 */
	public double getY() {
		return y;
	}
	
}
