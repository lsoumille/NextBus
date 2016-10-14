package bus;

import data.ManagerHoraires;
import graph.Field;
import graph.IFieldItem;
import graph.Simulator;
import utilisateurs.Utilisateur;
import utils.Position;
import utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 12/01/16.
 */
public class Bus implements IFieldItem{

    private Position posBus;
    private Map<String, Position> allStops;
    private BusStop lastStop;
    private ManagerHoraires managerH;
    private Iterator indLastStop;
    private List<Transport> allTransport;
    private Field field;

    public Bus(int nbLine, Field field) {
        managerH = new ManagerHoraires();
        this.allStops = managerH.getBusStopWithPos(nbLine);
        this.indLastStop = allStops.entrySet().iterator();
        Map.Entry<String, Position> entry = (Map.Entry)indLastStop.next();
        this.posBus = entry.getValue();
        this.lastStop = new BusStop(entry.getKey(), posBus, new Field(100,100));
        this.allTransport = new ArrayList<Transport>();
        this.field = field;
        this.field.placeInFirstPlace(this, posBus);
    }

    /**
     * Start the bus journey
     */
    public void launchBus(Simulator s){
        for( ; ; ){
            System.out.println("Bus is at : " + lastStop.getName());
            for(Transport trans : allTransport){
                trans.addStop(lastStop);
            }
            Utils.waitFunction(3000);
            if(!indLastStop.hasNext())
                indLastStop = allStops.entrySet().iterator();
            Map.Entry<String, Position> entry = (Map.Entry)indLastStop.next();
            List<Position> posBetweenStopAndNextStop = s.getPositionsFromTo(posBus, entry.getValue());
            for(Position p : posBetweenStopAndNextStop){
                Utils.waitFunction(50);
                s.move(this, posBus, p);
                for(Transport trans :allTransport)
                    trans.getUtilisateur().setPos(p);
                s.updateGrid();
                posBus = p;
            }

            lastStop = new BusStop(entry.getKey(), entry.getValue(), new Field(100,100));
        }
    }

    public Position getPosBus() {
        return posBus;
    }

    public void addTransport(Transport trans){
        allTransport.add(trans);
    }

    /**
     * remove the transport corresponding to the user in param
     * @param util
     * @return
     */
    public Transport detachTransport(Utilisateur util){
        for(Transport trans : allTransport)
            if(trans.getUtilisateur().equals(util)){
                allTransport.remove(trans);
                return trans;
            }
        return null;
    }

    public List<Transport> getAllTransport() {
        return allTransport;
    }

    public Color getColor() {
        return Color.yellow;
    }

    public void setField(Field field) {
        this.field = field;
        this.field.place(this, posBus);
    }
}
