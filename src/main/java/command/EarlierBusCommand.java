package command;

import serveur.ServiceThread;

/**
 * Created by user on 11/01/16.
 */
public class EarlierBusCommand extends CommandOnTimetable {

    public EarlierBusCommand() {
        super("EARLIERTIMETABLE");
    }

    /**
     * return the hour of the next bus for the requested bus stop
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        String nomArret = null;
        serveur.getMessenger().sendMessage("Veuillez entrer votre arret de bus:", serveur.getIc());
        while((nomArret = serveur.getMessenger().readMessage(serveur.getIc())) == null || ! managerH.isBusStop(nomArret))
            serveur.getMessenger().sendMessage("Arret de bus inconnu", serveur.getIc());
        answer.append(managerH.getNextHour(nomArret));
        return false;
    }
}
