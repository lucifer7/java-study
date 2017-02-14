package concurrency.ch4ThreadExecutor.delayedRunner;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/14
 **/
@Slf4j
public class DelayedRunnerMain {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        log.info("Main: starting at: {}.", new Date());

        for (int i = 0; i < 5; i++) {
            Task task = new Task("Task " + i);
            executor.schedule(task, i * 2, TimeUnit.SECONDS);
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Main: ends at: {}.", new Date());
    }
}
