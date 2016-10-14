package command;

import crypto.AES;
import crypto.Crypto;
import crypto.ICrypto;
import crypto.RSA;
import serveur.ServiceThread;

/**
 * Created by user on 14/01/16.
 */
public class CommandSecurityLevel extends Command {

    public CommandSecurityLevel() {
        super("SECURITYLEVEL");
    }

    /**
     * change the level of the security and start a handshake with the client
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        String niveau;
        serveur.getMessenger().sendMessage("Veuillez entrer le niveau de sécurité que vous souhaitez: 0 : Normal (SSL), 1 : AES, 2 : RSA", serveur.getIc());
        while((niveau = serveur.getMessenger().readMessage(serveur.getIc())) == null || ! (Integer.parseInt(niveau) <= 2) || ! (Integer.parseInt(niveau) >= 0))
            serveur.getMessenger().sendMessage("Ce niveau n'existe pas. Veuillez entrer un niveau entre 0 et 2:", serveur.getIc());
        int niv = Integer.parseInt(niveau);
        ICrypto oldCrypt = serveur.getIc();
        switch(niv)
        {
            case 1:
                serveur.setIc(new AES());
                break;
            case 2:
                serveur.setIc(new RSA());
                break;
            default:
                serveur.setIc(new Crypto());
                break;
        }
        serveur.getIc().handShake(serveur.getMessenger(), oldCrypt);
        answer.append("Le niveau " + niveau + " est activé.");
        return false;
    }
}
