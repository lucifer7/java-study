package concurrency.ch2ThreadSync.fairLock;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lucifer on 2016-7-21.
 */
@Log4j
public class PrintQueue {
    private final Lock lock = new ReentrantLock(true);

    public void printJob(Object document) {
        lock.lock();

        try {
            Long duration = (long)(Math.random() * 1000);
            System.out.println(Thread.currentThread().getName() + " Duration: " + duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
