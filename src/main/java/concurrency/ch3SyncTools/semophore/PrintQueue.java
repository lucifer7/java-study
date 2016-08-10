package concurrency.ch3SyncTools.semophore;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Semaphore;

/**
 * Created by lucifer on 2016-8-7.
 */
@Log4j
public class PrintQueue {
    public static final int PERMITS = 1;
    private final Semaphore semaphore;

    public PrintQueue() {
        this.semaphore = new Semaphore(PERMITS);        // build a binary semaphore
    }

    public void printJob(Object document) {
        try {
            semaphore.acquire();    //首先尝试获取信号量，可能抛异常
            long duration = (long) (Math.random() *10);
            log.info(String.format("%s PrintQueue printing last %d millis.", Thread.currentThread().getName(), duration));
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();    //释放信号量
        }
    }
}
