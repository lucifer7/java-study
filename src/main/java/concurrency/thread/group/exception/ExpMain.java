package concurrency.thread.group.exception;

import concurrency.thread.exception.ErrorTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by lucifer on 2016-6-25.
 */
public class ExpMain {
    public static void main(String[] args) {
        ExThreadGroup group = new ExThreadGroup("exceptionGroup");

        ErrorTask task = new ErrorTask();

        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(group, task);
            t.start();
        }

        try {
            TimeUnit.SECONDS.sleep(20l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

