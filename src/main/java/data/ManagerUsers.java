package data;

import utilisateurs.Utilisateur;
import utils.Utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 08/01/16.
 */
public class ManagerUsers extends Manager{

    public ManagerUsers() {
        super();
    }

    /**
     * check if the nickname is already used in the file at the path location
     * @param name
     * @param path
     * @return
     */
    public boolean userAlreadyExist(String name, String path){
        try {
            List<String[]> allUsers = gf.decryptedRead(path);
            for(String[] tabS : allUsers)
                if(tabS[0].toUpperCase().equals(name.toUpperCase()))
                    return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * check if the string contains semicolons
     * @param mdp
     * @return
     */
    public boolean checkSemicolonInMdp(String mdp){
        return mdp.contains(";");
    }

    /**
     * check if the password is good for the user
     * @param name
     * @param mdp
     * @param path
     * @return
     */
    public boolean isGoodMdpForUser(String name, String mdp, String path){
        try {
            List<String[]> allUsers = gf.decryptedRead(path);
            for(String[] tabS : allUsers)
                if(tabS[0].equals(name) && tabS[1].equals(mdp))
                    return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * check is the syntax of the mail is correct
     * @param mail
     * @return
     */
    public boolean isValidEmail(String mail){
        Pattern pat = Pattern.compile(Utils.EMAIL_PATTERN);
        Matcher match = pat.matcher(mail);
        return match.matches();
    }

    /**
     * Return the mail address for the name in param
     * @param name
     * @param path
     * @return
     */
    public String getMailForTheName(String name, String path){
        try {
            List<String[]> allUsers = gf.decryptedRead(path);
            for(String[] tabS : allUsers) {
                if (tabS[0].equals(name)) {
                    return tabS[2];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * update the information of the user in param in the user file
     * @param newUser
     * @param path
     * @return
     */
    public boolean setUserInformation(Utilisateur newUser, String path){
        try {
            List<String[]> allUsers = gf.decryptedRead(path);
            for(int i = 0 ; i < allUsers.size() ; ++i) {
                if(allUsers.get(i)[0].equals(newUser.getNom())) {
                    String[] tabToADD = new String[newUser.getArray().size()];
                    tabToADD = newUser.getArray().toArray(tabToADD);
                    allUsers.set(i,tabToADD);
                    break;
                }
            }
            gf.eraseFileContent(path);
            for(String[] tab : allUsers){
                gf.encryptedWrite(Arrays.asList(tab), path);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * return the account number of the user name in param
     * @param name
     * @param path
     * @return
     */
    public String getAccountNumberByName(String name, String path){
        try {
            List<String[]> allUsers = gf.decryptedRead(path);
            for(String[] tabS : allUsers)
                if(tabS[0].equals(name))
                    return tabS[5];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

