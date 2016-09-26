package concurrency.ch4ThreadExecutor.threadRunner;

import lombok.extern.log4j.Log4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> 任务类 </b>
 *
 * @author lucifer
 *         Date 2016-9-26
 *         Device Aurora R5
 */
@Log4j
public class Task implements Runnable {
    private Date initDate;
    private String name;
    String test;

    public Task(String name) {
        this.name = name;
        initDate = new Date();
    }

    @Override
    public void run() {
        log.info(String.format("%s: Task %s: created on %s.",
                Thread.currentThread().getName(), name, initDate));
        log.info(String.format("%s: Task %s: started on %s.",
                Thread.currentThread().getName(), name, new Date()));

        try {
            long duration = (long) (Math.random() * 10);
            log.info(String.format("%s: Task %s: processing task during %d seconds.",
                    Thread.currentThread().getName(), name, duration));
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(String.format("%s: Task %s: finished on %s.",
                Thread.currentThread().getName(), name, new Date()));
    }

}
