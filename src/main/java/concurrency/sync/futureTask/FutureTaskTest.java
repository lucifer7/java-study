package concurrency.sync.futureTask;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/2
 * Time: 13:52
 **/
@Log4j
public class FutureTaskTest {
    FutureTask futureTask;
    Thread thread;

    @Before
    public void initThread() {
        // 1.0 Initial
        Calculator calculator = new Calculator();
        futureTask = new FutureTask(calculator);
        thread = new Thread(futureTask);
    }

    @Test
    public void futureTaskTest() {
        // 2.0 Start
        log.info("----Thread Start at " + new Date().toLocaleString() + "----");
        thread.start();

        // 3.0 Check calculator state
        while(!futureTask.isDone()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("====Waiting for result.....");
        }

        // 4.0 Calculate finished
        log.info("----Thread Finish at " + new Date().toLocaleString() + "----");
        try {
            Integer result = (Integer) futureTask.get();
            log.info("----FINAL RESULT IS: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

@Log4j
class Calculator implements Callable {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(5000);
        log.info("----Calculating Finished----");
        return RandomUtils.nextInt(3000, 90000);
    }
}
