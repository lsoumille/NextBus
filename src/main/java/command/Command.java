package command;

import serveur.ServiceThread;


/**
 * Created by user on 08/01/16.
 */
public class Command {
    private String commandWord;

    public Command(String commandWord) {
        this.commandWord = commandWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    /**
     * process for the command return true to end the connection
     * @return
     */
    public boolean use(ServiceThread serveur, StringBuffer answer){
        return false;
    }

    public boolean hasSameCommandWord(Command cmd){
        return this.commandWord.toUpperCase().equals(cmd.getCommandWord().toUpperCase());
    }
}
