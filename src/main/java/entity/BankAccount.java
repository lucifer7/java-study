package entity;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/5/20
 * Time: 17:10
 **/
@Log4j
@Data
public class BankAccount {
    public static final Map<String, BankAccount> accountMap = new HashMap<>();

    private String uuid;
    private String username;
    private String currency;
    private float balance;

    // 提款业务
    public synchronized void withdraw(float amount) {
        float temp = balance;

        try {
            TimeUnit.MILLISECONDS.sleep(10);     //mock network latency
        } catch (InterruptedException e) {
            log.error("Withdraw interrupted.", e);
        }
        temp -= amount;
        this.balance = temp;
    }

    // 取款业务
    public synchronized void deposit(float amount) {
        float temp = balance;

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        temp += amount;
        this.balance = temp;
    }

    public float checkBalance() {
        return this.balance;
    }

    public static BankAccount genAccountByUuid(String uuid) {
        BankAccount account = new BankAccount();
        account.setUuid(uuid);
        account.setUsername("USER" + RandomUtils.nextInt(0, 1000));
        account.setBalance(RandomUtils.nextFloat(0, 20000f));
        account.setCurrency("CNY");
        log.info("Get BankAccount by uuid: " + account.toString());
        return account;
    }

    public static BankAccount genRandomAccount() {
        String uuid = "KEY:" + System.currentTimeMillis();
        return genAccountByUuid(uuid);
    }

}
