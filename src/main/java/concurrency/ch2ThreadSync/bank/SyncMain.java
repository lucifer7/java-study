package concurrency.ch2ThreadSync.bank;

import entity.BankAccount;
import lombok.extern.log4j.Log4j;

/**
 * Created by lucifer on 2016-6-25.
 */
@Log4j
// 由于账户里存款与取款业务都有synchronized 修饰，最终余额一致
public class SyncMain {
    public static void main(String[] args) {
        BankAccount account = BankAccount.genRandomAccount();
        log.info("Account=" + account.getUsername());
        log.info("Balance is $" + account.getBalance());

        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);

        Company company = new Company(account);
        Thread companyThread = new Thread(company);

        bankThread.start();
        companyThread.start();

        try {
            companyThread.join();
            bankThread.join();

            log.info("Balance in the account is now: $" + account.getBalance());
        } catch (InterruptedException e) {
            log.error("Exchange interrupted", e);
        }

    }
}
