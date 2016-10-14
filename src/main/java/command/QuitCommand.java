package command;

import serveur.ServiceThread;

/**
 * Created by user on 18/01/16.
 */
public class QuitCommand extends Command {

    public QuitCommand() {
        super("QUIT");
    }

    /**
     * says goodbye to the user
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        answer.append("Merci, au revoir");
        return true;
    }
}
