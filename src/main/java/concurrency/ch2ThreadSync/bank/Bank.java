package concurrency.ch2ThreadSync.bank;

import entity.BankAccount;

/**
 * Created by lucifer on 2016-6-25.
 */
public class Bank implements Runnable {
    private BankAccount account;

    public Bank(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.withdraw(10);
        }
    }
}
