package command;

import serveur.ServiceThread;
import utilisateurs.Utilisateur;
import utils.Utils;

/**
 * Created by user on 08/01/16.
 */
public class ConnectCommand extends CommandOnUsers {

    public ConnectCommand() {
        super("CONNECT");
    }

    /**
     * ask the pseudo and the mdp to the client and connect him if he exist in the database
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        String nom = null;
        serveur.getMessenger().sendMessage("Veuillez entrer un nom d'utilisateur:", serveur.getIc());
        while((nom = serveur.getMessenger().readMessage(serveur.getIc())) == null || ! manageU.userAlreadyExist(nom, Utils.pathToUser))
            serveur.getMessenger().sendMessage("Ce Pseudo n'existe pas. Veuillez entrer un autre nom d'utilisateur:", serveur.getIc());
        String mdp = null;
        serveur.getMessenger().sendMessage("Veuillez entrer votre nom de passe:", serveur.getIc());
        while((mdp =  serveur.getMessenger().readMessage(serveur.getIc())) == null || mdp.length() == 0 || ! manageU.isGoodMdpForUser(nom, mdp, Utils.pathToUser))
            serveur.getMessenger().sendMessage("Mot de passe invalide. Veuillez en saisir un autre :", serveur.getIc());
        serveur.setAllCommands(Utils.commandsUserConnected);
        serveur.setUser(new Utilisateur(nom, mdp, manageU.getMailForTheName(nom, Utils.pathToUser), Utils.sim.getField()));
        answer.append("Vous êtes connecté");
        return false;
    }
}
