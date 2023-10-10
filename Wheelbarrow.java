
public class Wheelbarrow {
	
	/**
	 * nazev kolecka
	 */
	private String name;
	/**
	 * minimalni rychlost kolecka
	 */
	private double vmin;
	/**
	 * maximalni rychlost kolecka
	 */
	private double vmax;
	/**
	 * minilani vzzdalenost potrebna pro opravu
	 */
	private double dmin;
	/**
	 * maximalni vzzdalenost potrebna pro opravu
	 */
	private double dmax;
	/**
	 * doba opravy kolecka
	 */
	private double td;
	/**
	 * maximalni pocet pytlu na kolecku
	 */
	private int kd;
	/**
	 * procentualni zastoupeni kolecka 
	 */
	private double pd;
	
	/**
	 * @param name
	 * @param vmin
	 * @param vmax
	 * @param dmin
	 * @param dmax
	 * @param td
	 * @param kd
	 * @param pd
	 */
	public Wheelbarrow(String name, double vmin, double vmax, double dmin, double dmax, double td, int kd, double pd) {
		this.name = name;
		this.vmin = vmin;
		this.vmax = vmax;
		this.dmin = dmin;
		this.dmax = dmax;
		this.td = td;
		this.kd = kd;
		this.pd = pd;
	}
	
	
}
