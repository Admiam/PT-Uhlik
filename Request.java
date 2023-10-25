
public class Request {
	
	/**
	 * cas prichodu pozadavku
	 */
	private double tz;
	/**
	 * ID zakaznika kteremu dorucujeme pozadavek
	 */
	private int customerID;
	/**
	 * pocet pozadovanych pytlu
	 */
	private int n;
	/**
	 * doba do ktere musi byt pozadavek vyrizen
	 */
	private double tp;
	
	/**
	 * Konstruktor
	 * @param tz
	 * @param customerID
	 * @param n
	 * @param tp
	 */
	public Request(double tz, int customerID, int n, double tp) {
		this.tz = tz;
		this.customerID = customerID;
		this.n = n;
		this.tp = tp;
	}
	
}
