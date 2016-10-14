import crypto.AES;
import crypto.Crypto;
import crypto.ICrypto;
import crypto.RSA;
import utils.Messenger;
import utils.Utils;

import javax.net.ssl.SSLSocket;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by user on 07/01/16.
 */
public class Client {

    private Map<String, ICrypto> allCryptoAvailable;

    private ICrypto ic;

    private Messenger mess;

    public Client() {
        ic = new Crypto();
        allCryptoAvailable = new HashMap<String, ICrypto>();
        allCryptoAvailable.put("AES", new AES());
        allCryptoAvailable.put("RSA", new RSA());
        allCryptoAvailable.put("CRYPTO", new Crypto());
    }

    public void launch(){
        try {
            // Le path courant du projet
            String path = new File("").getAbsolutePath();
            ProcessBuilder pb = new ProcessBuilder(path + "/script.sh");
            Process p = pb.start();
            p.waitFor();
            System.setProperty("javax.net.ssl.trustStore", "dest_keystore");
            InetAddress addr = Inet4Address.getByAddress(Utils.addresseServeur);
            SSLSocket sslsocket = (SSLSocket) Utils.k_FactoryClient.createSocket(addr, 9999);
            mess = new Messenger(sslsocket);

            Scanner sc = new Scanner(System.in);
            String messageEnvoye = "";
            System.out.println(mess.readMessage(ic));
            while(!messageEnvoye.toUpperCase().equals("QUIT")){
                messageEnvoye=sc.nextLine();
                mess.sendMessage(messageEnvoye, ic);
                String answer = mess.readMessage(ic);
                if(answer.equals("SYNCHRONIZE")){
                    handSkahe(mess);
                    answer = mess.readMessage(ic);
                }
                System.out.println(answer);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * update the encryption algorithm
     * @param mess
     */
    public void handSkahe(Messenger mess){
        mess.sendMessage("ACK", ic);
        ICrypto oldCrypto = ic;
        ic = getICryptoByString(mess.readMessage(ic));
        mess.sendMessage("ACK", oldCrypto);
        ic.handShakeClient(mess, oldCrypto);
    }

    private ICrypto getICryptoByString(String str){
        return allCryptoAvailable.get(str);
    }

    public static void main(String[] arstring) {
        Client c = new Client();
        c.launch();
    }
}
