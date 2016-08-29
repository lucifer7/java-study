package concurrency.executor.simpleThreadpool;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.TimeUnit;

/**
 * Usage: <b>具体的任务类</b>
 *
 * @author lucifer
 * @date 2016-8-29
 * @devide Yoga Pro
 */
@Log4j
public class MyTask implements Runnable {
    protected String name;

    public MyTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
