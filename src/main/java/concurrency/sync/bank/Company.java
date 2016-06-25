package concurrency.sync.bank;

import entity.BankAccount;

/**
 * Created by lucifer on 2016-6-25.
 */
public class Company implements Runnable {
    private BankAccount account;

    public Company(BankAccount account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.deposit(10);
        }
    }
}
