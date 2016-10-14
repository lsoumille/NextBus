package data;

import crypto.Blowfish;
import crypto.ICrypto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 07/01/16.
 */
public class GestionnaireFichier {

    private ICrypto algo;

    private static final char SEPARATEUR = ';';

    public GestionnaireFichier() {
        this.algo = new Blowfish();
    }

    /**
     * write the contents of the arraylist in the file in param
     * @param ligne
     * @param path
     * @throws IOException
     */
    public void write(List<String> ligne, String path) throws IOException {
        FileWriter fw = new FileWriter(new File(path),true);
        BufferedWriter bw = new BufferedWriter(fw);

        boolean first = true;
        for (String s : ligne) {
            if (first) {
                first = false;
            }
            else {
                bw.write(SEPARATEUR);
            }
            bw.write(s);
        }
        bw.write("\n");

        bw.close();
        fw.close();
    }

    /**
     * write the content of the arraylist in the file in param with blowfish algorithm
     * @param ligne
     * @param path
     * @throws IOException
     */
    public void encryptedWrite(List<String> ligne, String path) throws IOException {
        FileWriter fw = new FileWriter(new File(path),true);
        BufferedWriter bw = new BufferedWriter(fw);

        boolean first = true;
        for (String s : ligne) {
            if (first) {
                first = false;
            }
            else {
                bw.write(SEPARATEUR);
            }
            bw.write(algo.encrypt(s));
        }
        bw.write("\n");

        bw.close();
        fw.close();
    }

    /**
     * Return the content of the file in param
     * @param path
     * @return
     * @throws IOException
     */
    public List<String[]> read(String path) throws IOException {
        List<String[]> message = new ArrayList<String[]>();
        FileReader fr = new FileReader(new File(path));
        BufferedReader br = new BufferedReader(fr);

        for (String s = br.readLine(); s != null; s = br.readLine()) {
            String[] data = s.split(String.valueOf(SEPARATEUR));
            message.add(data);
        }
        br.close();
        fr.close();
        return message;
    }

    /**
     * Return the decrypted content of the file in param
     * @param path
     * @return
     * @throws IOException
     */
    public List<String[]> decryptedRead(String path) throws IOException {
        List<String[]> message = new ArrayList<String[]>();
        FileReader fr = new FileReader(new File(path));
        BufferedReader br = new BufferedReader(fr);

        for (String s = br.readLine(); s != null; s = br.readLine()) {
            String[] data = s.split(String.valueOf(SEPARATEUR));
            for (int i = 0; i < data.length; i++) {
                data[i] = algo.decrypt(data[i]);
            }
            message.add(data);
        }
        br.close();
        fr.close();
        return message;
    }

    /**
     * delete the content of a file
     * @param path
     */
    public void eraseFileContent(String path){
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAlgo(ICrypto ic) {
        algo =  ic;
    }

}
