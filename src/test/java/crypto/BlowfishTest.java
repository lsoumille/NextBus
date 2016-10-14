package crypto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by user on 08/01/16.
 */
public class BlowfishTest {
    Blowfish algo;
    String key;
    String input;
    String encrypted;
    String decrypted;

    @Before
    public void setUp(){
        key = "MySecretKey";
        input = "Bonjour";
        encrypted = "C3I7mbMJKnE=";
        decrypted = "Bonjour";
        algo = new Blowfish(key);
    }

    /**
     * check if the encryption is correct with the blowfish algorithm
     */
    @Test
    public void testEncrypt(){
        String inputEncrypted = algo.encrypt(input);
        assertEquals(inputEncrypted, encrypted);
    }

    /**
     * check if the decryption file:///home/user/ogl/pom.xmlis correct with the blowfish algorithm
     */
    @Test
    public void testDecrypt(){
        Blowfish bl = new Blowfish("CASPAR-F");
        String inputDecrypted = bl.decrypt("n7ybUb0TKek=");
        assertEquals(inputDecrypted, decrypted);
    }
}
