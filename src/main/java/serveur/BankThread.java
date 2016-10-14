package serveur;

import bank.Bank;

/**
 * Created by user on 14/01/16.
 */
public class BankThread implements Runnable {

    private Bank bank;

    public BankThread() {
        this.bank = new Bank();
    }

    public void run(){
        bank.processPayment();
    }
}
