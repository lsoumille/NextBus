package command;

import serveur.ServiceThread;

/**
 * Created by user on 11/01/16.
 */
public class NearestStopCommand extends CommandOnTimetable {

    public NearestStopCommand() {
        super("NEARESTSTOP");
    }

    /**
     * return the user nearest bus stop
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        answer.append(managerH.getNearestStop(serveur.getUser().getPos()));
        return false;
    }
}
