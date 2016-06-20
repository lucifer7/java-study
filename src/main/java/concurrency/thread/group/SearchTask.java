package concurrency.thread.group;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/20
 * Time: 15:34
 **/
@Log4j
public class SearchTask implements Runnable {
    private Result result;

    public SearchTask(Result result) {
        this.result = result;
    }

    @Override
    public void run() {
        log.info(Thread.currentThread().getName() + " started ---");

        try {
            doTask();
            result.setName(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            log.error(Thread.currentThread().getName() + " is interrupted.");
            //e.printStackTrace();
        }

        log.info(Thread.currentThread().getName() + " end. ");
    }

    private void doTask() throws InterruptedException {
        TimeUnit.SECONDS.sleep(RandomUtils.nextInt(0, 10));
    }
}
