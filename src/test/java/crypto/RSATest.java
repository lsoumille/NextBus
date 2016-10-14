package crypto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by user on 14/01/16.
 */
public class RSATest {
    RSA algo1;
    RSA algo2;
    String input;
    String inputEncrypted;

    @Before
    public void setUp(){
        input = "Bonjour";
        algo1 = new RSA();
        algo2 = new RSA();
        algo1.setKey(algo2.getSecretKey());
        algo2.setKey(algo1.getSecretKey());
        inputEncrypted = algo1.encrypt(input);
    }

    /**
     * check if the encryption is correct with the RSA algorithm
     */
    @Test
    public void testEncrypt(){
        inputEncrypted = algo1.encrypt(input);
        assertNotEquals(inputEncrypted, input);
    }

    /**
     * check if the decryption is correct with the RSA algorithm
     */
    @Test
    public void testDecrypt(){
        String inputDecrypted = algo2.decrypt(inputEncrypted);
        assertEquals(inputDecrypted, input);
    }
}
