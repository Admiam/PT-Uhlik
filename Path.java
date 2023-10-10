
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
	 * @param warehouse
	 * @param customer
	 */
	public Path(int warehouseID, int customerID) {
		this.warehouseID = warehouseID;
		this.customerID = customerID;
	}
	

}
