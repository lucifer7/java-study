package concurrency.ch2ThreadSync.readWriteLock;

import lombok.extern.log4j.Log4j;

/**
 * Created by lucifer on 2016-7-24.
 */
@Log4j
public class Writer implements Runnable {
    private PricesInfo pricesInfo;

    public Writer(PricesInfo pricesInfo) {
        this.pricesInfo = pricesInfo;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            log.info("Writer: is ready.");
            pricesInfo.setPrice(Math.random()*10, Math.random()*10);
            log.info("Writer: prices are updated.");

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
