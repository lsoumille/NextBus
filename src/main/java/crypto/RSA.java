package crypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.Messenger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created by user on 14/01/16.
 */
public class RSA implements ICrypto {
    private Key publicKey;
    private PrivateKey privateKey;
    private Cipher rsaCipher;
    private Key publicKeyOther;

    public RSA() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
            rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encrypt the string in param with rsa algorithm
     * @param toEncrypt
     * @return the encrypted string
     */
    public String encrypt(String toEncrypt) {
        try {
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKeyOther);
            byte[] byteCipherText = rsaCipher.doFinal(toEncrypt.getBytes());
            String nn =  new BASE64Encoder().encode(byteCipherText);
            return nn.replace("\n", "");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Decrypt the string in param with rsa algorithm
     * @param toDecrypt
     * @return the decrypted string
     */
    public String decrypt(String toDecrypt) {
        try {
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] byteDecryptedText = rsaCipher.doFinal(new BASE64Decoder().decodeBuffer(toDecrypt));
            return new String(byteDecryptedText);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * start handshake with the client and send its public key
     * receive the client public key
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
        //send key
        String keySent = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        System.out.println("Clé RSA envoyée : " + keySent);
        mess.sendMessage(keySent, oldCrypt);
        //receive client key
        byte[] decodedKey = Base64.getDecoder().decode(mess.readMessage(oldCrypt));
        try {
            publicKeyOther = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedKey)); //new SecretKeySpec(decodedKey, 0, decodedKey.length, this.toString());
            System.out.println("Clé RSA reçue " +  publicKeyOther);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * receive the server public key and send its public key
     * @param mess
     * @param oldCrypt
     */
    public void handShakeClient(Messenger mess, ICrypto oldCrypt) {
        byte[] decodedKey = Base64.getDecoder().decode(mess.readMessage(oldCrypt));
        try {
            publicKeyOther = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedKey)); //new SecretKeySpec(decodedKey, 0, decodedKey.length, this.toString());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        mess.sendMessage(Base64.getEncoder().encodeToString(publicKey.getEncoded()), oldCrypt);
    }

    public void setKey(Key key) {
        try {
            this.publicKeyOther = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key.getEncoded()));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Key getSecretKey() {
        return publicKey;
    }

    @Override
    public String toString() {
        return "RSA";
    }
}
