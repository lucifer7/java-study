package java8.ch6stream;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-8-13
 *         Device Aurora R5
 */
@Slf4j
public class CollectorHarness {
    public static void main(String[] args) {
        long fatest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            Map<Boolean, List<Integer>> map =  partitionPrimesWithCustomCollector(1_000_000);
            long duration = (System.nanoTime() - start) / 1_000_000;
            fatest = fatest > duration ? duration : fatest;
            log.info("Execution {}, time cost {} ms.", i + 1, duration);
        }
        log.info("Fastest execution time cost: {} ms.", fatest);
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
    }
}
