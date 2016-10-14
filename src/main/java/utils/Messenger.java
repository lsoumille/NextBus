package utils;

import crypto.ICrypto;

import java.io.*;
import java.net.Socket;

/**
 * Created by user on 08/01/16.
 */
public class Messenger {
    private BufferedReader in;
    private BufferedWriter out;

    public Messenger(Socket socketToBind) {
        try {
            in = new BufferedReader(new InputStreamReader(socketToBind.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socketToBind.getOutputStream()));
        } catch (IOException IOE) {
            System.err.println(IOE);
        }
    }

    /**
     * write the message in param in the bufferedwriter
     * @param str
     */
    public void sendMessage(String str, ICrypto ic){
        try {
            out.write(ic.encrypt(str));
            out.newLine();
            out.flush();
        } catch (IOException IOE) {
            System.err.println(IOE);
        }
    }

    /**
     * return the message in the bufferedreader
     * @return
     */
    public String readMessage(ICrypto ic){
        try {
            return ic.decrypt(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
