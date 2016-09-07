package concurrency.ch3SyncTools.semophore;

/**
 * 使用信号量控制并发访问多个资源
 * Created by lucifer on 2016-8-7.
 */
public class SemaphoreMain {

    public static final int SIZE = 10;

    public static void main(String[] args) {
        // 1. Initial
        PrintQueue printQueue = new PrintQueue();

        // 2. Build thread array
        Thread[] threads = new Thread[SIZE];
        for (int i = 0; i < SIZE; i++) {
            threads[i] = new Thread(new Job(printQueue), "Job-" + i);
        }

        // 3. Start threads
        for (int i = 0; i < SIZE; i++) {
            threads[i].start();
        }
    }
}
