package utils;

import bus.Bus;
import command.*;
import graph.Field;
import graph.Simulator;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 07/01/16.
 */
public class Utils {
    //Instance used to create ServerSocket with ssl
    public static SSLServerSocketFactory k_FactoryServ =
            (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

    //Instance used to create Socket with ssl
    public static SSLSocketFactory k_FactoryClient = (SSLSocketFactory) SSLSocketFactory.getDefault();

    //Default key used to encrypt and decrypt
    public static final String DEFAULT = "CASPAR-F";

    // address of the server
    public static final byte[] addresseServeur = new byte[] {(byte)10, (byte)212, (byte)109, (byte)210};

    //A list with all the commands available for an unknown user
    public static List<Command> commandsWithoutAuth = new ArrayList<Command>() {{
        add(new CreateUserCommand());
        add(new ConnectCommand());
        add(new QuitCommand());
    }};

    //A list with all the commands available for a known user
    public static List<Command> commandsUserConnected = new ArrayList<Command>() {{
        add(new TimetableCommand());
        add(new EarlierBusCommand());
        add(new NearestStopCommand());
        add(new BankDetailsCommand());
        add(new InCommand());
        add(new CommandSecurityLevel());
        add(new QuitCommand());
    }};

    //A list with all the commands available for a know user in a bus
    public static List<Command> commandsUserConnectedInBus = new ArrayList<Command>(){{
        add(new TimetableCommand());
        add(new EarlierBusCommand());
        add(new NearestStopCommand());
        add(new BankDetailsCommand());
        add(new OutCommand());
        add(new CommandSecurityLevel());
        add(new QuitCommand());
    }};

    //path of the file containing users's mdp
    public static final String pathToUser = "bd/mdp.csv";

    //path of the file containing all the bank accounts
    public static final String PATH_TO_BANK = "bd/bank.csv";

    //path of the file containing all the transactions
    public static final String PATH_TO_TRANSACTIONS = "bd/transaction.csv";

    //path of the file containing bus timetable
    public static final String pathToTimetable = "bd/horaire";
    public static final String CSV = ".csv";

    //Syntax of an email
    public static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //Syntax of a bank account
    public static final String BANK_ACCOUNT_PATTERN =
            "^(\\d){16}$";

    //Object of email code validation
    public static final String ObjectMail = "Code de validation NextBus";

    //Object of billing email
    public static final String OBJECT_MAIL_FACTURE = "Facture des transactions";

    //Message of email code validation
    public static final String MessageMail = "Bonjour,\n Merci pour votre inscription à l'application NextBus\n + " +
            "Voici votre code d'activiation : ";

    //Message of billing email
    public static final String FactureMail = "Bonjour,\n\n \tVous trouverez la facture de votre dernière transaction ci-joint.\n\nCordialement,\nL'équipe NEXTBUSCASPARF.";

    //create a code when a new user try to subscribe
    public static String generateCode(){
        Random rand = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz123456789@!?,'#&%";
        int longueur = alphabet.length();
        String code = "";
        for(int i = 0; i < 8; i++) {
            int k = rand.nextInt(longueur);
            code += alphabet.charAt(k);
        }
        return code;
    }

    //List with all the numbers of the line
    public static List<Integer> allBusNumbers = new ArrayList<Integer>(){{
        add(12);
        add(2);
    }};

    //Check if a string can be cast in int
    public static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),10) < 0) return false;
        }
        return true;
    }

    //GUI
    public static Simulator sim;

    //Bus simulation on line 12
    //public static Bus busL12 = new Bus(12, new Field(100,100));

    //Bus simulation on line 2
    public static Bus busL2 = new Bus(2, new Field(100,100));

    //list with all the bus
    public static List<Bus> allBus = new ArrayList<Bus>(){{
       // add(busL12);
        add(busL2);
    }};

    //Price of a ride between two bus stops
    public static double PRICE_RIDE = 0.1;

    //wait the time in param
    public static void waitFunction(int time){
        try{
            Thread.sleep(time);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
