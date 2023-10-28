
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
	 * 
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
	
	public double getX() {
		return x;
	}
		
	public double getY() {
		return y;
	}
	public double getTs(){ return ts; }
	public double getTn(){ return tn; }
	
}
