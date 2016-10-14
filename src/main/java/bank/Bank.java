package bank;

import data.ManagerBank;
import data.ManagerUsers;
import utils.Utils;

import java.util.List;

/**
 * Created by user on 14/01/16.
 */
public class Bank {

    ManagerBank mb;
    ManagerUsers mu;

    public Bank() {
        this.mb = new ManagerBank();
        this.mu = new ManagerUsers();
    }

    /**
     * Each minute try to cash all the new transactions
     */
    public void processPayment(){
        for( ; ; ){
            Utils.waitFunction(60000);
            System.out.println("-- Start Processing new payments --");
            List<Transaction> allTransaction = mb.readAllTransaction();
            for(int i = 0 ; i < allTransaction.size() ; ++i){
                if(! allTransaction.get(i).isPaid()
                    && tryToPayIn(allTransaction.get(i))){
                    allTransaction.get(i).payementReceived();

                }
            }
            mb.rewriteAllTransaction(allTransaction);
            System.out.println("-- End Processing new payments --");
        }
    }

    /**
     * check if the transaction is cashable
     * @param trans
     * @return
     */
    private boolean tryToPayIn(Transaction trans){
        String bankAccount = mu.getAccountNumberByName(trans.getTrans().getUtilisateur().getNom(), Utils.pathToUser);
        if(bankAccount == null) return false;
        mb.cashTheMontantForTheAccountNumber(bankAccount, trans.getMontant());
        return true;
    }
}
