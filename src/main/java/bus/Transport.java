package bus;

import graph.Field;
import utilisateurs.Utilisateur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/01/16.
 */
public class Transport {

    private Utilisateur utilisateur;
    private List<BusStop> stopsOnJourney;

    public Transport(Utilisateur utilisateur, BusStop firstStop) {
        this.utilisateur = utilisateur;
        this.stopsOnJourney = new ArrayList<BusStop>();
        stopsOnJourney.add(firstStop);
    }

    public Transport(String name, List<BusStop> stopsOnJourney) {
        this.utilisateur = new Utilisateur(name, null, null, new Field(100,100));
        this.stopsOnJourney = stopsOnJourney;
    }

    public void addStop(BusStop name){
        stopsOnJourney.add(name);
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public List<BusStop> getStopsOnJourney() {
        return stopsOnJourney;
    }
}
