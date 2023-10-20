
public class Path {
	
	/**
	 * cislo skladu odkoud/kam vede cesta
	 */
	private int warehouseID;
	/**
	 * cislo zakaznika odkoud/kam vede cesta
	 */
	private int customerID;
	/**
	 * vzdalenost dane cesty
	 */
	private double distance;
	
	/**
	 * @param warehouse
	 * @param customer
	 */
	public Path(int warehouseID, int customerID) {
		this.warehouseID = warehouseID;
		this.customerID = customerID;
	}
	
	/**
	 * Metoda pocitajici delku cesty
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void calculateDistance(double x1, double y1, double x2, double y2) {
		
		double x = x2-x1;
		double y = y2-y1;
		
		this.distance = Math.sqrt( (x*x) + (y*y) );
		
	}
	
	public double getDistance() {
		return distance;
	}
	

}
