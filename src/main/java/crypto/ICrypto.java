package crypto;

import utils.Messenger;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.security.Key;

/**
 * Created by user on 08/01/16.
 */
public interface ICrypto {

    //Function used to encrypt the string toEncrypt
    String encrypt(String toEncrypt);

    //Function used to decrypt the string toDecrypt
    String decrypt(String toDecrypt);

    //Synchronization between the server and the client about security level(server side)
    void handShake(Messenger mess, ICrypto oldCrypt);

    //Synchronization between the server and the client about security level(client side)
    void handShakeClient(Messenger mess, ICrypto oldCrypt);

    void setKey(Key key);

    //return the key for encryption/decryption
    Key getSecretKey();
}
