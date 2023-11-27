import java.util.Random;

/**
 * Trida reprezentujici kolecka a jejich typy
 * @author TR
 *
 */
public class Wheelbarrow {
	
	/**
	 * nazev kolecka
	 */
	public String name;
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
	private double velocity;
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
	 * @param name jmeno kolecka
	 * @param vmin min rychlost
	 * @param vmax max rychlost
	 * @param dmin min vzdalenost do udrzby
	 * @param dmax max vzdalenost do udrzby
	 * @param td doba opravy
	 * @param kd pocet pytlu
	 * @param pd pravdepodobnost kolecka
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
	 * Metoda vypisujici jmeno kolecka
	 *
	 * @return vraci jmeno kolecka
	 */
	public String getName(){
		return name;
	}

	/**
	 * Konstruktor pro vygenerovani kolecka na zaklade jeho druhu
	 * @param type druh kolecka
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
	 * @param vx max rychlost
	 * @param vn min rychlost
	 * @return vraci vyslednou rychlost
	 */
	private double generateVelocity(double vx, double vn){
		double v = vn + (vx - vn) * rd.nextDouble();
		return v;
	}
	
	/**
	 * Metoda generujici vzdalenost do udrzby kolecka
	 * @param dmax maximalni vzdalenost
	 * @param dmin minimalni vzdalenost
	 * @return vraci vzdalenost do udrzby 
	 */
	private double generateDistance(double dmax, double dmin) {

		double mi = (dmin + dmax) / 2;  
        double sigma = (dmax - dmin) / 4; 

        double dfinal = rd.nextGaussian() * sigma + mi;
		return dfinal;
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
	 * @return vraci pocet pytlu kolecka
	 */
	public int getVolume() {
		return kd;
	}

	/**
	 * Getter pro rychlost kolecka
	 * @return vraci rychlost kolecka
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * Getter pro vzdalenost potrebnou do udrzby
	 * @return vraci vzdalenost do udrzby
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Getter pro ziskani pravdepodobnosti druhu kolecka
	 * @return vraci pravdepodobnost druhu kolecka
	 */
	public double getProbability() {
		return pd;
	}
	
	/**
	 * Getter pro ziskani id kolecka
	 * @return vraci id kolecka
	 */
	public int getID() {
		return id;
	}

	/**
	 * Vypis id pro kontrolu
	 */
	public void printID() {
		System.out.println("ID: " + id);
	}
	
	/**
	 * Getter na maximalni vzdalenost
	 * @return vraci max vzdalenost
	 */
	public double getDMax(){
		return dmax;
	}
	
}
