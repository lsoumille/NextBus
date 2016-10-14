package command;

import data.ManagerUsers;

/**
 * Created by user on 08/01/16.
 */
public class CommandOnUsers extends Command {

    protected ManagerUsers manageU;

    public CommandOnUsers(String commandWord) {
        super(commandWord);
        this.manageU = new ManagerUsers();
    }
}
