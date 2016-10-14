package command;

import bus.Bus;
import bus.BusStop;
import bus.Transport;
import graph.Field;
import serveur.ServiceThread;
import utils.Position;
import utils.Utils;

/**
 * Created by user on 12/01/16.
 */
public class InCommand extends CommandOnTimetable {

    public InCommand() {
        super("IN");
    }

    /**
     * client try to take the bus
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        Position posUser = serveur.getUser().getPos();
        String arret = managerH.getNearestStop(posUser);
        boolean busAvailable = false;
        for(Bus bus : Utils.allBus){
            if(bus.getPosBus().equals(posUser)){
                Transport newTransport = new Transport(serveur.getUser(), new BusStop(arret,bus.getPosBus(), new Field(100,100)));
                bus.addTransport(newTransport);
                busAvailable = true;
                break;
            }
        }
        if(busAvailable){
            answer.append("Vous etes dans le bus");
            serveur.setAllCommands(Utils.commandsUserConnectedInBus);
            serveur.getUser().addNumTransactions();
        } else {
            answer.append("Pas de bus disponible");
        }
        return false;
    }
}
