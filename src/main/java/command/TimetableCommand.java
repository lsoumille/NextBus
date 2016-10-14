package command;

import serveur.ServiceThread;
import utils.Utils;

/**
 * Created by user on 11/01/16.
 */
public class TimetableCommand extends CommandOnTimetable {
    public TimetableCommand() { super("TIMETABLE"); }

    /**
     * return all the information concerning the bus
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        String busStop = null;
        serveur.getMessenger().sendMessage("Veuillez entrer le numéro de la ligne de bus : ", serveur.getIc());
        while ((busStop = serveur.getMessenger().readMessage(serveur.getIc())) == null ||
                busStop.length() == 0 || !Utils.isInteger(busStop) || !Utils.allBusNumbers.contains(Integer.parseInt(busStop))) {
            serveur.getMessenger().sendMessage("Cette ligne de bus n'existe pas. Réessayez : ", serveur.getIc());
        }
        //Il faudrait choisir le bon fichier en fonction de l'entrée
        int lineNb = Integer.parseInt(busStop);
        answer.append(managerH.getTimetable(lineNb).toString());
        return false;
    }
}
