package crypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import utils.Messenger;
import utils.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by user on 08/01/16.
 */
public class Blowfish implements ICrypto {

    private String key;

    public Blowfish() {
        this(Utils.DEFAULT);
    }

    public Blowfish(String key) {
        this.key = key;
    }

    /**
     * Encrypt the string in param with blowfish algorithm
     * @param toEncrypt
     * @return
     */
    public String encrypt(String toEncrypt) {
        byte[] keyData = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(toEncrypt.getBytes());
            return new BASE64Encoder().encode(hasil);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
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
     * Decrypt the string in param with blowfish algorithm
     * @param toDecrypt
     * @return
     */
    public String decrypt(String toDecrypt) {
        try{
            byte[] keyData = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(new BASE64Decoder().decodeBuffer(toDecrypt));
            return new String(hasil);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
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

    public void handShake(Messenger mess, ICrypto oldCrypt) {
        return;
    }

    public void handShakeClient(Messenger mess, ICrypto olICrypto) {
        return;
    }

    public void setKey(Key key) {
        return;
    }

    public Key getSecretKey() {
        return null;
    }

    @Override
    public String toString() {
        return "BLOWFISH";
    }
}
