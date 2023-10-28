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
	
	private int velocity;
	
	private double distance;
	
	private static int count = 0;
	
	private int id;
	
	private Random rd = new Random();
	
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
		this.id = 0;
	}
	
	public Wheelbarrow(Wheelbarrow type) {
		this.name = type.name;
		this.velocity = generateVelocity(type.vmax, type.vmin);
		this.distance = generateDistance(type.dmax, type.dmin);
		this.td = type.td;
		this.kd = type.kd;
		this.pd = -1;
		count++;
		this.id = count;
			
	}

	private int generateVelocity(double vx, double vn){
		int v = rd.nextInt(((int)vx) - ((int)vn) + 1) + ((int)vn);
		return v;
	}
	
	private double generateDistance(double dx, double dn) {
		//TODO: Vypocet pomoci gausova klobouku
		double d = dx-dn;
		return d;
	}

	public double getRepairTime() {
		return td;
	}

	public int getVolume() {
		return kd;
	}

	public int getVelocity() {
		return velocity;
	}

	public double getDistance() {
		return distance;
	}
	
	public double getProbability() {
		return pd;
	}
	
	public int getID() {
		return id;
	}
	
}
