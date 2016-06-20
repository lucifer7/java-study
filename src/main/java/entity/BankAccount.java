package entity;

import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Map;

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

    public void withdraw(float amount) {
        this.balance -= amount;
    }

    public void deposit(float amount) {
        this.balance += amount;
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
