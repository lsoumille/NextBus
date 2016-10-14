package data;

import bank.Transaction;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 14/01/16.
 */
public class ManagerBank extends Manager {

    public ManagerBank() {
        super();
    }

    /**
     * check if the string in param has a syntax of a bank account
     * @param nbAccount
     * @return
     */
    public boolean checkBankAccountSyntax(String nbAccount){
        Pattern pat = Pattern.compile(Utils.BANK_ACCOUNT_PATTERN);
        Matcher match = pat.matcher(nbAccount);
        return match.matches();
    }

    /**
     * check if the number in param is an existing bank account
     * @param nbAccount
     * @return
     */
    public boolean isABankAccount(String nbAccount) {
        try {
            if(! checkBankAccountSyntax(nbAccount))
                return false;
            List<String[]> allBankAccounts = gf.decryptedRead(Utils.PATH_TO_BANK);
            for(String[] tabS : allBankAccounts)
                if(tabS[0].equals(nbAccount))
                    return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * write the transaction in param in the file containing all the transactions
     * @param trans
     * @return
     */
    public boolean writeTransaction(Transaction trans){
        if(trans.getMontant() != 0.0 && trans.getTrans().getUtilisateur() != null
                                     && trans.getTrans().getStopsOnJourney() != null){
            try {
                gf.encryptedWrite(trans.toArray(), Utils.PATH_TO_TRANSACTIONS);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * remove the content of the transaction file and write all the transactions in param in it
     * @param allTrans
     */
    public void rewriteAllTransaction(List<Transaction> allTrans){
        gf.eraseFileContent(Utils.PATH_TO_TRANSACTIONS);
        for(Transaction trans :allTrans)
            writeTransaction(trans);

    }

    /**
     * return all the transactions in the transaction file
     * @return
     */
    public List<Transaction> readAllTransaction(){
        try {
            List<String[]> allTransactionsInStr = gf.decryptedRead(Utils.PATH_TO_TRANSACTIONS);
            List<Transaction> allTransactions = new ArrayList<Transaction>();
            for(String[] tabStr : allTransactionsInStr){
                List<String> allStopsInTransac = new ArrayList<String>();
                for(int i = 3 ; i < tabStr.length ; ++i)
                    allStopsInTransac.add(tabStr[i]);
                allTransactions.add(new Transaction(tabStr[0], tabStr[1], tabStr[2], allStopsInTransac));
            }
            return allTransactions;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * cash the amount (montant) for the account number
     * @param nbAccount
     * @param montant
     */
    public void cashTheMontantForTheAccountNumber(String nbAccount, double montant){
        try {
            List<String[]> allAccounts = gf.decryptedRead(Utils.PATH_TO_BANK);
            for(String[] str : allAccounts) {
                if (str[0].equals(nbAccount)) {
                    double oldMontant = Double.parseDouble(str[1]);
                    oldMontant -= montant;
                    str[1] = Double.toString(oldMontant);
                    break;
                }
            }
            gf.eraseFileContent(Utils.PATH_TO_BANK);
            for(String[] str : allAccounts){
                gf.encryptedWrite(Arrays.asList(str), Utils.PATH_TO_BANK);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * return bank balance for the account in param
     * @param nbAccount
     * @return
     */
    public double getMontantForBankAccount(String nbAccount){
        List<String[]> allAccounts = null;
        try {
            allAccounts = gf.decryptedRead(Utils.PATH_TO_BANK);
            for(String[] str : allAccounts) {
                if (str[0].equals(nbAccount)) {
                    return Double.parseDouble(str[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
