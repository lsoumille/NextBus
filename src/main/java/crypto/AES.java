package crypto;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.Messenger;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by user on 14/01/16.
 */
public class AES implements ICrypto {
    private Key secretKey;
    private Cipher aesCipher;

    public AES() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            secretKey = keyGen.generateKey();

            aesCipher = Cipher.getInstance("AES");//BadPaddingException: Given final block not properly padded
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt the string in param with aes algorithm
     * @param toEncrypt
     * @return the encrypted string
     */
    public String encrypt(String toEncrypt) {
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] byteCipherText = aesCipher.doFinal(toEncrypt.getBytes());
            String nn = new BASE64Encoder().encode(byteCipherText);
            return nn.replace("\n", "");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * Decrypt the string in param with aes algorithm
     * @param toDecrypt
     * @return the decrypted string
     */
    public String decrypt(String toDecrypt) {
        try {
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] byteDecryptedText = aesCipher.doFinal(new BASE64Decoder().decodeBuffer(toDecrypt));

            return new String(byteDecryptedText);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * messages sent to tell to the user to use AES encryption
     * @param mess
     * @param oldCrypt
     */
    public void handShake(Messenger mess, ICrypto oldCrypt) {
        mess.sendMessage("SYNCHRONIZE", oldCrypt);
        if(! mess.readMessage(oldCrypt).equals("ACK"))
            return;
        mess.sendMessage(this.toString(), oldCrypt);
        if(! mess.readMessage(oldCrypt).equals("ACK"))
            return;
        String clé = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("envoi de la clé AES : " + clé);
        mess.sendMessage(clé, oldCrypt);
        if(! mess.readMessage(oldCrypt).equals("ACK"))
            return;
    }

    /**
     * message received by the client to use AES Encryption
     * @param mess
     * @param oldICrypto
     */
    public void handShakeClient(Messenger mess, ICrypto oldICrypto) {
        String ans = mess.readMessage(oldICrypto);
        byte[] decodedKey = Base64.getDecoder().decode(ans);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, this.toString());
        this.setKey(originalKey);
        mess.sendMessage("ACK", oldICrypto);
    }


    public void setKey(Key key) {
        this.secretKey = key;
    }

    @Override
    public String toString() {
        return "AES";
    }

    public Key getSecretKey() {
        return secretKey;
    }
}
