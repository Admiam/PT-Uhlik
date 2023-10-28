import java.util.Random;

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
	 * maximalni vzdalenost potrebna pro opravu
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
	 * vysledna rychlost generovaneho kolecka
	 */
	private int velocity;
	/**
	 * vyslednou urazenou drahu do udrzby generovaneho kolecka
	 */
	private double distance;
	
	/**
	 * pocet kolecek
	 */
	private static int count = 0;
	
	/**
	 *id  kolecka
	 */
	private int id;
	
	/**
	 * random generator
	 */
	private Random rd = new Random();
	
	/**
	 * Konstrktor pro vytvoreni typu kolecka
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
		this.id = 0;
		this.velocity = 0;
		this.distance = 0;
	}
	
	/**
	 * Konstrktor pro vygenerovani kolecka na zaklade jeho druhu
	 * @param type
	 */
	public Wheelbarrow(Wheelbarrow type) {
		this.name = type.name;
		this.velocity = generateVelocity(type.vmax, type.vmin);
		this.distance = generateDistance(type.dmax, type.dmin);
		this.vmin = type.vmin;
		this.vmax = type.vmax;
		this.dmin = type.dmin;
		this.dmax = type.dmax;
		this.td = type.td;
		this.kd = type.kd;
		this.pd = -1;
		count++;
		this.id = count;
			
	}

	/**
	 * Metoda generujici rychlost kolecka
	 * @param vx
	 * @param vn
	 * @return
	 */
	private int generateVelocity(double vx, double vn){
		int v = rd.nextInt(((int)vx) - ((int)vn) + 1) + ((int)vn);
		return v;
	}
	
	/**
	 * Metoda generujici vzdalenost do udrzby kolecka
	 * @param dx
	 * @param dn
	 * @return
	 */
	private double generateDistance(double dx, double dn) {
		//TODO: Vypocet pomoci gausova klobouku
		double d = dx-dn;
		return d;
	}

	/**
	 * Getter pro cas potrebny na opravu kolecka
	 * @return
	 */
	public double getRepairTime() {
		return td;
	}

	/**
	 * Getter pro objem (pocet pytlu) kolecka
	 * @return
	 */
	public int getVolume() {
		return kd;
	}

	/**
	 * Getter pro rychlost kolecka
	 * @return
	 */
	public int getVelocity() {
		return velocity;
	}

	/**
	 * Getter pro vzdalenost potrebnou do udrzby
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Getter pro ziskani pravdepodobnosti druhu kolecka
	 * @return
	 */
	public double getProbability() {
		return pd;
	}
	
	/**
	 * Getter pro ziskani id kolecka
	 * @return
	 */
	public int getID() {
		return id;
	}
	
}
