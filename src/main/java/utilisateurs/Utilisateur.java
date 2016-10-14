package utilisateurs;

import graph.Field;
import graph.IFieldItem;
import utils.Position;
import utils.Utils;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by user on 07/01/16.
 */
public class Utilisateur implements IFieldItem{

    private String nom;
    private String mdp;
    private String mail;
    private Position pos;
    private String numBankAccount;
    private int numTransactions;
    private Field field;

    /**
     * Constructeur d'utilisateur
     * Rajoute en même temps dans le gestionnaire de fichier le nom et le mdp correspondant
     * @param nom
     * @param mdp
     */
    public Utilisateur(String nom, String mdp, String mail, Field field) {
        this.nom = nom;
        this.mdp = mdp;
        this.mail = mail;
        pos = new Position();
        //Random r1 = new Random();
        pos.setLongitude(10 /*r1.nextInt(101)*/);
        //Random r2 = new Random();
        pos.setLatitude(10/*r2.nextInt(101)*/);
        numBankAccount = "";
        numTransactions = 0;
        this.field = field;
        this.field.placeInFirstPlace(this, pos);
    }

    /**
     * Return the information about the user in an arraylist
     * @return
     */
     public ArrayList<String> getArray() {
         ArrayList<String> u = new ArrayList<String>();
         u.add(nom);
         u.add(mdp);
         u.add(mail);
         u.add(Integer.toString(pos.getLatitude()));
         u.add(Integer.toString(pos.getLongitude()));
         u.add(numBankAccount);
         return u;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        Utils.sim.move(this,this.pos, pos);
        this.pos = pos;
    }

    public void setField(Field field) {
        this.field = field;
        this.field.place(this, pos);
    }

    public void setNumBankAccount(String numBankAccount) {
        this.numBankAccount = numBankAccount;
    }

    public String getNom() {
        return nom;
    }

    public String getNumBankAccount() {
        return numBankAccount;
    }

    public int getNumTransactions() {
        return numTransactions;
    }

    public void addNumTransactions() {
        numTransactions ++;
    }

    public String getMail() {
        return mail;
    }

    public Color getColor() {
        return Color.cyan;
    }
}

