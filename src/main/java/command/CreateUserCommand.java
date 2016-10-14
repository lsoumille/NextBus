package command;

import graph.Field;
import utilisateurs.Utilisateur;
import serveur.ServiceThread;
import utils.Utils;

import java.io.IOException;

/**
 * Created by user on 08/01/16.
 */
public class CreateUserCommand extends CommandOnUsers {

    public CreateUserCommand() {
        super("CREATE");
    }

    /**
     * ask all the information to the client and create a new user
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        String nom = null;
        serveur.getMessenger().sendMessage("Veuillez entrer un nom d'utilisateur:", serveur.getIc());
        while((nom = serveur.getMessenger().readMessage(serveur.getIc())) == null || nom.length() == 0 || manageU.userAlreadyExist(nom, Utils.pathToUser))
            serveur.getMessenger().sendMessage("Pseudo-existant. Veuillez entrer un autre nom d'utilisateur:",serveur.getIc());
        String mdp = null;
        serveur.getMessenger().sendMessage("Veuillez entrer votre mot de passe (sans ;):", serveur.getIc());
        while((mdp =  serveur.getMessenger().readMessage(serveur.getIc())) == null || mdp.length() == 0 || manageU.checkSemicolonInMdp(mdp))
            serveur.getMessenger().sendMessage("Mot de passe invalide. Veuillez en saisir un autre :", serveur.getIc());
        String mail = null;
        serveur.getMessenger().sendMessage("Veuillez entrer votre adresse mail:", serveur.getIc());
        while((mail = serveur.getMessenger().readMessage(serveur.getIc())) == null || mail.length() == 0 || ! manageU.isValidEmail(mail))
            serveur.getMessenger().sendMessage("Adresse mail invalide. Veuillez en saisir une autre :", serveur.getIc());
        String code = Utils.generateCode();
        serveur.getMailer().sendMail(mail, Utils.ObjectMail, Utils.MessageMail + code);
        serveur.getMessenger().sendMessage("Veuillez entrer le code reçu par mail:", serveur.getIc());
        String inputCode = null;
        while((inputCode = serveur.getMessenger().readMessage(serveur.getIc())) == null || inputCode.length() == 0 || ! inputCode.equals(code))
            serveur.getMessenger().sendMessage("Code invalide. Veuillez le consulter dans votre mail :", serveur.getIc());
        Utilisateur u = new Utilisateur(nom, mdp, mail, new Field(100,100));
        try {
            serveur.getGestFich().encryptedWrite(u.getArray(), Utils.pathToUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        answer.append("Utilisateur créé");
        return false;
    }
}
