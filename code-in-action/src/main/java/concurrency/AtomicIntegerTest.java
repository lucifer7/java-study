package concurrency;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by yangjingyi on 2016-5-24.
 */
public class AtomicIntegerTest {
    @Test
    public void atomicIntegerTest() throws InterruptedException {
        final AtomicInteger atomicInteger = new AtomicInteger(10);
        assertEquals(atomicInteger.compareAndSet(1, 2), false);
        assertEquals(atomicInteger.get(), 10);

        assertTrue(atomicInteger.compareAndSet(10, 3));
        assertEquals(atomicInteger.get(), 3);
        atomicInteger.set(0);
        //
        assertEquals(atomicInteger.incrementAndGet(), 1);
        assertEquals(atomicInteger.getAndAdd(2), 1);
        assertEquals(atomicInteger.getAndSet(5), 3);
        assertEquals(atomicInteger.get(), 5);

        final int threadSize = 10;
        Thread[] ts = new Thread[threadSize];

        for ( int i = 0; i < threadSize; i++) {
            ts[i] = new Thread() {
                public void run() {
                    atomicInteger.incrementAndGet();
                }
            };
        }

        for ( Thread tr : ts) {
            tr.start();
        }
        for ( Thread tr : ts) {
            tr.join();
        }

        assertEquals(atomicInteger.get(), 5 + threadSize);
    }

}

