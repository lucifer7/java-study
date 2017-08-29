package concurrency.cancelInterrupt.interruptedCancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * Created by lucifer on 2016-7-1.
 */
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    public void run() {
        BigInteger p = BigInteger.ONE;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        interrupt();
    }
}
