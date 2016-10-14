package command;

import data.ManagerHoraires;

/**
 *
 * Created by user on 11/01/16.
 */
public class CommandOnTimetable extends Command {
    protected ManagerHoraires managerH;

    public CommandOnTimetable(String commandWord) {
        super(commandWord);
        managerH = new ManagerHoraires();
    }

}
