package crypto;

import utils.Messenger;

import java.security.Key;

/**
 * Created by user on 15/01/16.
 */
public class Crypto implements ICrypto {

    public Crypto() {
    }

    public String encrypt(String toEncrypt) {
        return toEncrypt;
    }

    public String decrypt(String toDecrypt) {
        return toDecrypt;
    }

    /**
     * says to the client thaht he shouldn't use an encryption algorithm
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
    }

    public void handShakeClient(Messenger mess, ICrypto oldICrypto) {
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
        return "CRYPTO";
    }
}
