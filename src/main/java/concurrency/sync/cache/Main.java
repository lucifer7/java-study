package concurrency.sync.cache;


import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yangjingyi on 2016-6-1.
 */
@Log4j
public class Main {
    @Test
    public void memorizer1Test() {
        Computable caculator = new ExpensiveFunction();
        final Computable cache = new MemorizerSync(caculator);

        for (int i = 0; i < 20; i++) {
            final String arg = RandomUtils.nextLong(100000L, 100015L) + "";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info(cache.compute(arg));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void memorizer2Test() {
        Computable caculator = new ExpensiveFunction();
        final Computable cache = new MemorizerConcurrent(caculator);
        final AtomicInteger counter = new AtomicInteger(1);

        for (int i = 0; i < 20; i++) {
            final String arg = RandomUtils.nextLong(200000L, 200015L) + "";
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        log.info("-----Thread No. " + counter);
                        log.info(cache.compute(arg));
                        counter.getAndIncrement();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
