package command;

import org.junit.Before;
import org.junit.Test;
import serveur.ServiceThread;

import javax.net.ssl.SSLSocket;

import static org.junit.Assert.*;
/**
 * Created by user on 11/01/16.
 */
public class CommandTest {

    Command c1;
    Command c2;
    Command c3;

    ServiceThread st;
    StringBuffer answer;

    String s1 = "create";
    String s2 = "connect";

    @Before
    public void setUp() {
        c1 = new Command(s1);
        c2 = new Command(s1);
        c3 = new Command(s2);
    }

    @Test
    public void useTest() {
        boolean use = c1.use(st, answer);
        assertFalse(use);
    }

    @Test
    public void hasSameCommandWordTest() {
        assertTrue(c1.hasSameCommandWord(c2));
        assertFalse(c1.hasSameCommandWord(c3));
    }
}
