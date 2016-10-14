package serveur;

import bus.Bus;
import graph.Simulator;
import utils.Utils;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;
import java.io.IOException;

/**
 * Created by user on 07/01/16.
 */
public class Serveur {

    SSLServerSocket sslserversocket;

    public Serveur() {
        try {
            System.setProperty("javax.net.ssl.keyStore", "NextBusKey");
            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
            sslserversocket = (SSLServerSocket) Utils.k_FactoryServ.createServerSocket(9999);
            Utils.sim = new Simulator();
            for(Bus bus : Utils.allBus)
                new Thread(new BusThread(bus)).start();
            new Thread(new BankThread()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The server wait for client connections
     */
    private void boucleAttente(){
        try{
            while(true){
                new Thread(new ServiceThread((SSLSocket)sslserversocket.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            Serveur serv = new Serveur();
            serv.boucleAttente();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

