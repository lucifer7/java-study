package concurrency.inaction;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> suspend() and resume() are not recommended, cause suspend() won't release LOCK when pause thread </b>
 * And if resume() executed before suspend(), thread won't recover
 * And the state of thread will always be <b>Runnable</b>
 * May use LockSupport.part(), LockSupport.unpark(t1) instead
 *
 * @author Jingyi.Yang
 *         Date 2017/9/2
 **/
@Slf4j
public class BadSuspend {
    public static Object u = new Object();
    static ChangeObjectThread t1 = new ChangeObjectThread();
    static ChangeObjectThread t2 = new ChangeObjectThread();

    static class ChangeObjectThread extends Thread {
        @Override
        public void run() {
            synchronized (u) {
                log.info("Lock acquired by {}", getName());
                Thread.currentThread().suspend();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        TimeUnit.SECONDS.sleep(1L);
        t2.start();

        t1.resume();
        t2.resume();

        t1.join();
        t2.join();
    }

}
