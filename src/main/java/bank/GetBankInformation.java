package bank;

import data.ManagerBank;
import data.ManagerUsers;
import utils.Utils;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * Created by user on 19/01/16.
 */
public class GetBankInformation {

    ManagerBank mb;
    ManagerUsers mu;

    public GetBankInformation() {
        this.mb = new ManagerBank();
        this.mu = new ManagerUsers();
    }

    public static void main(String[] args) {
        GetBankInformation gbi = new GetBankInformation();
        Scanner sc = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat ( ) ;
        df.setMaximumFractionDigits (2); //arrondi à 2 chiffres apres la virgules
        df.setMinimumFractionDigits (2);
        df.setDecimalSeparatorAlwaysShown (true);
        System.out.println("Current bank balance : " + df.format(gbi.mb.getMontantForBankAccount(gbi.mu.getAccountNumberByName(sc.nextLine(), Utils.pathToUser))));
    }
}
