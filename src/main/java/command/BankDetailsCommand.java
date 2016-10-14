package command;

import crypto.ICrypto;
import crypto.RSA;
import data.ManagerBank;
import serveur.ServiceThread;
import utils.Utils;

/**
 * Created by user on 14/01/16.
 */
public class BankDetailsCommand extends CommandOnUsers {

    private ManagerBank mb;

    public BankDetailsCommand() {
        super("BANKDETAILS");
        mb = new ManagerBank();
    }

    /**
     * change the encryption algoritm to RSA and ask account number to the client
     * @param serveur
     * @param answer
     * @return
     */
    @Override
    public boolean use(ServiceThread serveur, StringBuffer answer) {
        ICrypto oldCrypt = serveur.getIc();
        RSA algoSecure = null;
        if (!oldCrypt.toString().equals("RSA")) {
            algoSecure = new RSA();
            serveur.setIc(algoSecure);
            serveur.getIc().handShake(serveur.getMessenger(), oldCrypt);
        }
        String account = null;
        serveur.getMessenger().sendMessage("Veuillez entrer votre numero de compte bancaire:", serveur.getIc());
        while((account = serveur.getMessenger().readMessage(serveur.getIc())) == null
                || account.length() == 0
                || ! mb.checkBankAccountSyntax(account)
                || ! mb.isABankAccount(account))
            serveur.getMessenger().sendMessage("Numero de compte incorrect. Veuillez entrer un autre Numero de compte :",serveur.getIc());
        serveur.getUser().setNumBankAccount(account);
        manageU.setUserInformation(serveur.getUser(), Utils.pathToUser);
        if (algoSecure != null) {
            serveur.setIc(oldCrypt);
            serveur.getIc().handShake(serveur.getMessenger(), algoSecure);
        }
        answer.append("Informations bancaires ajoutées");
        return false;
    }
}
