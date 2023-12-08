package pt;
/**
 * Trida reprezentujici pozadavek
 * @author TR
 *
 */
public class Request {

    /**
     * cas prichodu pozadavku
     */
    private final double tz;
    /**
     * ID zakaznika kteremu dorucujeme pozadavek
     */
    private final int customerID;
    /**
     * pocet pozadovanych pytlu
     */
    private final int n;
    /**
     * doba do ktere musi byt pozadavek vyrizen
     */
    private final double tp;

    /**
     * Konstruktor
     * @param tz cas prichodu
     * @param customerID ID zakaznika
     * @param n pocet pytlu
     * @param tp cas vyrizeni
     */
    public Request(double tz, int customerID, int n, double tp) {
        this.tz = tz;
        this.customerID = customerID;
        this.n = n;
        this.tp = tp;
    }
    /**
     * getter na cas prichodu
     * @return vraci cas prichodu
     */
    public double getTz(){
        return tz;
    }
    /**
     * getter na id zakaznika
     * @return vraci id zakaznika
     */
    public int getID(){
        return customerID;
    }
    /**
     * getter na pocet pytlu
     * @return vraci pocte pytlu
     */
    public int getN(){
        return n;
    }
    /**
     * getter na cas vyrizeni
     * @return vraci cas vyrizeni
     */
    public double getTp(){
        return tp;
    }

}
