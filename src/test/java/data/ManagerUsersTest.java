package data;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 11/01/16.
 */
public class ManagerUsersTest {

    String fileTestPath = "src/test/java/bdTest/mdp.csv";
    ManagerUsers managerUsers;
    ArrayList<String> user;

    String name = "lucas";
    String mdp = "lucas";
    String wrongMdp = "sarita;";
    String mail = "lucassoumille@yahoo.fr";
    String wrongMail = "lkdlkfl.fr@@";

    @Before
    public void setUp() {
        managerUsers = new ManagerUsers();
        user = new ArrayList<String>();
        user.add(name);
        user.add(mdp);
        user.add(mail);
        try {
            managerUsers.gf.encryptedWrite(user, fileTestPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestUserAlreadyExist() {
        boolean exist = managerUsers.userAlreadyExist(name, fileTestPath);
        boolean wrongExist = managerUsers.userAlreadyExist("Mickael", fileTestPath);
        assertTrue(exist);
        assertFalse(wrongExist);
    }

    @Test
    public void TestCheckSemicolonInMdp() {
        boolean existColon = managerUsers.checkSemicolonInMdp(wrongMdp);
        boolean wrongExistColon = managerUsers.checkSemicolonInMdp(mdp);
        assertTrue(existColon);
        assertFalse(wrongExistColon);
    }

    @Test
    public void TestIsGoodMdpForUser() {
        boolean isGood = managerUsers.isGoodMdpForUser(name, mdp, fileTestPath);
        boolean isWrong = managerUsers.isGoodMdpForUser(name, wrongMdp, fileTestPath);
        assertTrue(isGood);
        assertFalse(isWrong);
    }

    @Test
    public void TestIsValidEmail() {
        boolean valid = managerUsers.isValidEmail(mail);
        boolean invalid = managerUsers.isValidEmail(wrongMail);
        assertTrue(valid);
        assertFalse(invalid);
    }

    @Test
    public void testGetMailForTheName(){
        String mailFile = managerUsers.getMailForTheName(name, fileTestPath);
        Assert.assertEquals(mail,mailFile);
    }

    @After
    public void flushFile(){
        try {
            FileOutputStream fos = new FileOutputStream(fileTestPath);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
