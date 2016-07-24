package concurrency.ch2ThreadSync.readWriteLock;

import lombok.extern.log4j.Log4j;

/**
 * Created by lucifer on 2016-7-24.
 */
@Log4j
public class Reader implements Runnable {
    private PricesInfo pricesInfo;

    public Reader(PricesInfo pricesInfo) {
        this.pricesInfo = pricesInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            log.info(Thread.currentThread().getName() + " Price1: " + pricesInfo.getPrice1());
            log.info(Thread.currentThread().getName() + " Price2: " + pricesInfo.getPrice2());
        }
     }
}
