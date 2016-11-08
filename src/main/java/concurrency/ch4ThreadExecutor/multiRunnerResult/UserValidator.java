package concurrency.ch4ThreadExecutor.multiRunnerResult;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-10-24
 *         Device Aurora R5
 */
@Log4j
public class UserValidator {
    private String name;

    public UserValidator(String name) {
        this.name = name;
    }

    public boolean validate(String name, String password) throws InterruptedException {
        long duration = RandomUtils.nextInt(2000);
        log.info(Thread.currentThread().getName() + ": validate for " + duration + " ms.");
        TimeUnit.MILLISECONDS.sleep(duration);
        return duration % 2 == 0;
        //return new Random().nextBoolean();
    }
}
