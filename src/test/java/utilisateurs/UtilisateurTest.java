package utilisateurs;

import graph.Field;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by user on 13/01/16.
 */
public class UtilisateurTest {

    Utilisateur utilisateur;
    ArrayList<String> u;

    String name;
    String wrongName;
    String mdp;
    String mail;

    @Before
    public void setUp() {
        name = "Toto";
        wrongName = "Titi";
        mdp = "totomdp";
        mail = "toto@gmail.com";

        utilisateur = new Utilisateur("Toto", "totomdp", "toto@gmail.com", new Field(100,100));
        u = new ArrayList<String>();

        u.add(name);
        u.add(mdp);
        u.add(mail);
        u.add(Integer.toString(utilisateur.getPos().getLatitude()));
        u.add(Integer.toString(utilisateur.getPos().getLongitude()));
    }

    @Test
    public void getArrayTest() {
        assertEquals(u, utilisateur.getArray());
        assertNotEquals(wrongName, u.get(0));
    }
}
