package serveur;

import bus.Bus;
import utils.Utils;

/**
 * Created by user on 12/01/16.
 */
public class BusThread implements Runnable {

    private Bus bus;

    public BusThread(Bus bus) {
        this.bus = bus;
    }

    public void run() {
        bus.launchBus(Utils.sim);
    }
}
