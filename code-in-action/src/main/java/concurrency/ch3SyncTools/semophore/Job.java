package concurrency.ch3SyncTools.semophore;

import lombok.extern.log4j.Log4j;

/**
 * Created by lucifer on 2016-8-7.
 */
@Log4j
public class Job implements Runnable {
    private PrintQueue printQueue;

    public Job(PrintQueue printQueue) {
        this.printQueue = printQueue;
    }

    @Override
    public void run() {
        log.info(String.format("%s will print a job.", Thread.currentThread().getName()));

        printQueue.printJob(new Object());

        log.info(String.format("%s finish printing job.", Thread.currentThread().getName()));
    }
}
