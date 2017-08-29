package concurrency.ch2ThreadSync.syncCondition;

import java.util.concurrent.TimeUnit;

/**
 * Created by lucifer on 2016-7-20.
 *
 * 带条件的同步控制，使用wait() 等待，释放锁, notify()通知，不释放锁，随机通知, notifyAll()通知，不释放锁，通知所有等待线程
 */
public class SyncMain {
    public static void main(String[] args) {
        EventStorage storage = new EventStorage();

        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);

        Thread pThread = new Thread(producer);
        Thread cThread = new Thread(consumer);

        cThread.start();
        pThread.start();
    }
}
