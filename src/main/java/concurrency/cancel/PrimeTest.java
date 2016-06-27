package concurrency.cancel;

import java.math.BigInteger;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by lucifer on 2016-6-27.
 */
public class PrimeTest {
    List<BigInteger> aSecondOfPrimes() {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();

        try {
            SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            generator.cancel();
        }
        return generator.get();
    }
}
