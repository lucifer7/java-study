package concurrency.ch2ThreadSync.fairLock;

/**
 * Created by lucifer on 2016-7-23.
 */
public class PrintJob implements Runnable {
    private PrintQueue printQueue;

    public PrintJob(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        System.out.println("A doc will print in" + Thread.currentThread().getName());
        printQueue.printJob(new Object());
        System.out.println("Doc is printed in " + Thread.currentThread().getName());
    }
}
