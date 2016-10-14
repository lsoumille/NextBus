package bank;

import bus.BusStop;
import bus.Transport;
import graph.Field;
import utils.Position;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 14/01/16.
 */
public class Transaction {

    private Transport trans;
    private double montant;
    private boolean isPaid;

    public Transaction(Transport trans) {
        this.trans = trans;
        this.montant = 0;
        int nbStops = trans.getStopsOnJourney().size();
            montant = Utils.PRICE_RIDE * (nbStops - 1);
        this.isPaid = false;
    }

    public Transaction(String nom, String montant, String isPaid, List<String> nameStop) {
        List<BusStop> busStopsInTransac = new ArrayList<BusStop>();
        for(String str : nameStop)
            busStopsInTransac.add(new BusStop(str, new Position(), new Field(100,100)));
        this.trans = new Transport(nom, busStopsInTransac);
        this.montant = Double.parseDouble(montant);
        this.isPaid = Boolean.parseBoolean(isPaid);
    }

    public Transport getTrans() {
        return trans;
    }

    public double getMontant() {
        return montant;
    }

    public boolean isPaid() {
        return isPaid;
    }

    /**
     * return the information of the transaction in a List
     * @return
     */
    public List<String> toArray(){
        List<String> t = new ArrayList<String>();
        t.add(trans.getUtilisateur().getNom());
        t.add(Double.toString(montant));
        t.add(Boolean.toString(isPaid));
        for(BusStop vs : trans.getStopsOnJourney())
            t.add(vs.getName());
        return t;
    }

    public void payementReceived(){
        this.isPaid = true;
    }
}
