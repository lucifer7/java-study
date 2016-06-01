package concurrency.sync.cache;


import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Created by yangjingyi on 2016-6-1.
 */
@Log4j
public class Main {
    public static void main(String[] args) {
        Computable caculator = new ExpensiveFunction();
        Computable cache = new Memoizer1(caculator);

        for (int i = 0; i < 200; i++) {
            String arg = RandomUtils.nextLong() + "";
            try {
                log.info(cache.compute(arg));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
