package serveur;

import command.Command;
import crypto.Crypto;
import crypto.ICrypto;
import data.GestionnaireFichier;
import utilisateurs.Utilisateur;
import utils.Mail;
import utils.Messenger;
import utils.Utils;

import javax.net.ssl.SSLSocket;
import java.util.List;

/**
 * Created by user on 09/01/16.
 */
public class ServiceThread implements Runnable {
    private SSLSocket sslSocket;

    private List<Command> allCommands;

    private Messenger messenger;

    private GestionnaireFichier gestFich;

    private Mail mailer;

    private Utilisateur user;

    private ICrypto ic;

    public ServiceThread(SSLSocket sock) {
        this.allCommands = Utils.commandsWithoutAuth;
        sslSocket = sock;
        ic = new Crypto();
        messenger = new Messenger(sock);
        gestFich = new GestionnaireFichier();
        mailer = new Mail();
    }

    /**
     * process executed for each thread
     */
    public void run() {
        String messageClient = "";
        messenger.sendMessage("Vous êtes connecté au serveur ! Envoyez votre premiere requête", ic);
        //Communication
        boolean finished = false;
        StringBuffer answer;
        while ((!finished)) {
            answer = new StringBuffer();
            messageClient = messenger.readMessage(ic);
            //si le client quitte sans prévenir
            if (messageClient == null) {
                break;
            }
            Command commandReq = new Command(messageClient.toUpperCase());
            finished = traiterCommande(commandReq, answer);
            //On envoie le message au client

            messenger.sendMessage(answer.toString(), ic);
        }

    }

    /**
     * Process the command sent by the client
     * @param cmd
     * @param answer
     * @return
     *
     */
    private boolean traiterCommande(Command cmd, StringBuffer answer) {
        Command usableCommand = getUsableCommand(cmd);
        if(usableCommand == null) {
            answer.append("Commande inconnue");
            return false;
        }
            /* arguments */
        return usableCommand.use(this, answer);
    }

    /**
     * Return the usable command corresponding to the command in parameter
     * @param cmd
     * @return
     */
    private Command getUsableCommand(Command cmd){
        for(Command c : allCommands){;
            if(c.hasSameCommandWord(cmd))
                return c;
        }
        return null;
    }

    public GestionnaireFichier getGestFich() {
        return gestFich;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public void setAllCommands(List<Command> allCommands) {
        this.allCommands = allCommands;
    }

    public Mail getMailer() {
        return mailer;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public Utilisateur getUser() {
        return user;
    }

    public ICrypto getIc() {
        return ic;
    }

    public void setIc(ICrypto ic) {
        this.ic = ic;
    }
}
