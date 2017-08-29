package concurrency.ch2ThreadSync.fairLock;

/**
 * Created by lucifer on 2016-7-23.
 */
public class LockMain {
    public static void main(String[] args) {
        PrintQueue printQueue = new PrintQueue();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new PrintJob(printQueue), "P-Thread" + i);
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
