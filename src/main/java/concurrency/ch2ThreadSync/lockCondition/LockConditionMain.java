package concurrency.ch2ThreadSync.lockCondition;

/**
 * Created by lucifer on 2016-7-31.
 */
public class LockConditionMain {

    public static final int CONSUMER_SIZE = 10;

    public static void main(String[] args) {
        // 1. load data
        FileMock file = new FileMock(100, 10);
        Buffer buffer = new Buffer(20);

        // 2.1 Initial producer
        Producer producer = new Producer(file, buffer);
        Thread pThread = new Thread(producer, "Th-Producer");

        // 2.2 Initial consumer
        Consumer[] consumers = new Consumer[CONSUMER_SIZE];
        Thread[] cThreads = new Thread[CONSUMER_SIZE];
        for (int i = 0; i < CONSUMER_SIZE; i++) {
            consumers[i] = new Consumer(file, buffer);
            cThreads[i] = new Thread(consumers[i], "Th-Consumer" + i);
        }

        // 3. Start
        pThread.start();
        for (int i = 0; i < CONSUMER_SIZE; i++) {
            cThreads[i].start();
        }
    }
}
