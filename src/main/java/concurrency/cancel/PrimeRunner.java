package concurrency.cancel;

import lombok.extern.log4j.Log4j;

import java.math.BigInteger;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by lucifer on 2016-6-27.
 */
@Log4j
public class PrimeRunner {
    static List<BigInteger> aSecondOfPrimes() {
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

    public static void main(String[] args) {
        List<BigInteger> primes = aSecondOfPrimes();
        for (BigInteger prime : primes) {
            log.info(prime);
        }
    }
}
