
public class Customer extends Vertex {

	/**
	 * kartezske souradnic kazdeho zakaznika x
	 */
	private double x;
	/**
	 * kartezske souradnic kazdeho zakaznika y
	 */
	private double y;
	
	/**
	 * Konstruktor
	 * @param x
	 * @param y
	 */
	public Customer(double x, double y) {
		this.x = x;
		this.y = y;

	}
	
	/**
	 * Getter pro ziskani souradnice X zakaznika
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter pro ziskani souradnice Y zakaznika
	 */
	public double getY() {
		return y;
	}
}
