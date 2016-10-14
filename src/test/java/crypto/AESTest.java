package crypto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Created by user on 14/01/16.
 */
public class AESTest {
    AES algo;
    String input;
    String inputEncrypted;

    @Before
    public void setUp(){
        input = "Bonjour";
        algo = new AES();
        inputEncrypted = algo.encrypt(input);
    }

    /**
     * check if the encryption is correct with the AES algorithm
     */
    @Test
    public void testEncrypt(){
        assertNotEquals(inputEncrypted, input);
    }

    /**
     * check if the decryption is correct with the AES algorithm
     */
    @Test
    public void testDecrypt(){
        String inputDecrypted = algo.decrypt(inputEncrypted);
        assertEquals(inputDecrypted, input);
    }
}
