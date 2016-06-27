package concurrency.cancelInterrupt.blockedCancel;

import lombok.extern.log4j.Log4j;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by lucifer on 2016-6-27.
 */
@Log4j
public class PrimesConsumer {
    private boolean  needMorePrimes = true;

    void consumePrimes() {
        BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(50);  //producer is blocked when queue is full, thus cancel action has no response
        BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
        producer.start();

        try {
            while(needMorePrimes()) {
                consume(primes.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            producer.cancel();
        }
    }

    private boolean needMorePrimes() {
        return needMorePrimes;
    }

    private void consume(BigInteger prime) {
        log.info("Consuming prime: " + prime);
        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
        } finally {
            needMorePrimes = false;
        }
    }
}
