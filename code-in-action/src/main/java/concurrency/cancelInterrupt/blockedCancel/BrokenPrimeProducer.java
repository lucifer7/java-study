package concurrency.cancelInterrupt.blockedCancel;

import lombok.extern.log4j.Log4j;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lucifer on 2016-6-27.
 * 不可靠的取消操作，将生产者置于阻塞操作中
 */
@Log4j
public class BrokenPrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled = false;


    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        BigInteger p = BigInteger.ONE;

        try {
            while (!cancelled) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(Thread.currentThread().getName() + " is cancelled.");
    }

    public void cancel() {
        cancelled = true;
        log.info(Thread.currentThread().getName() + " is cancelling....");
    }

}
