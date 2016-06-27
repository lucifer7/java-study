package concurrency.cancelInterrupt.blockedCancel;

import org.junit.Test;

/**
 * Created by lucifer on 2016-6-27.
 */
public class ConsumerTest {
    @Test
    public void testPrimeConsumer() {
        new PrimesConsumer().consumePrimes();
    }
}
