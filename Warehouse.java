import java.util.Stack;

/**
 * Trida reprezentujici sklad
 * @author Tomas Rychestky
 *
 */
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
	 * pocet pytlu ks, ktere jsou do skladu vzdy po uplynuti doby ts doplneny.
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
	 * aktualni pocet pytlu ve skladu
	 */
	private int bc;
	/**
	 * posledni cas doplneni
	 */
	private double lastTs;
	/**
	 * Zasobnik kolecek
	 */
	static WheelbarrowStack wheelbarrows = new WheelbarrowStack();
	
	/**
	 * Konstruktor
	 * @param x kartezske souradnic skladu x
	 * @param y kartezske souradnic skladu y 
	 * @param ks pocet doplnenych pytlu
	 * @param ts  doba doplneni zasob
	 * @param tn doba nalozeni pytlu
	 */
	public Warehouse(double x, double y, int ks, double ts, double tn) {
		this.x = x;
		this.y = y;
		this.ks = ks;
		this.ts = ts;
		this.tn = tn;
		this.lastTs = 1;
		this.bc = ks;
	}
	
	/**
	 * Getter pro ziskani souradnice X skladu
	 * @return vraci souradnici X skladu
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Getter pro ziskani souradnice Y skladu
	 * @return vraci souradnici Y skladu
	 */
	public double getY() {
		return y;
	}
	/**
	 * Getter pro cas doplneni
	 * @return vraci cas doplneni
	 */
	public double getTs(){ return ts; }
	/**
	 * Getter pro cas nalozeni
	 * @return vraci cas nalozeni
	 */
	public double getTn(){ return tn; }
	
	/**
	 * Getter pro ziskani poctu pytlu ve skladu
	 * @return vraci pocet pytlu
	 */
	public int getBc() {
		return bc;
	}
	
	/**
	 * Getter pro ziskani casu posledniho doplneni
	 * @return vraci posledni cas doplneni
	 */
	public double getLastTs() {
		return lastTs;
	}

	/**
	 * Setter pro nastaveni poctu pytlu
	 * @param bc novy pocet pytlu
	 */
	public void setBc(int bc) {
		this.bc = bc;
	}

	/**
	 * Setter pro nastaveni posledniho casu doplneni
	 * @param lastTs novy posledni cas
	 */
	public void setLastTs(double lastTs) {
		this.lastTs = lastTs;
	}
	
	/**
	 * Getter pro ziskani pocetu doplnovanych pytlu
	 * @return vraci pocet doplnovanych pytlu
	 */
	public int getKs() {
		return ks;
	}
	
	/**
	 * Vrati kolecko ze zasobniku skladu
	 * @return kolecko
	 */
	public Wheelbarrow popWheel() {
		return wheelbarrows.pop();
	}
	
	/**
	 * Prida kolecko do zasobniku skladu
	 * @param wheel vkladane kolecko
	 */
	public void pushWheel(Wheelbarrow wheel) {
		wheelbarrows.push(wheel);
	}
	
	/**
	 * odstrani kolecko ze zasobniku skladu
	 * @param wheel odstranovane kolecko
	 */
	public void remWheel(Wheelbarrow wheel) {
		wheelbarrows.remove(wheel);
	}
	
	public boolean emptyWheel() {
		return wheelbarrows.isEmpty();
	}
	
	 public Stack<Wheelbarrow> cloneWheel() {
	        return wheelbarrows.clone();
	 }

}
