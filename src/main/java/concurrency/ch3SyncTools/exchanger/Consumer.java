package concurrency.ch3SyncTools.exchanger;

import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.concurrent.Exchanger;
import java.util.function.Predicate;

/**
 * Usage: <b> Consumer for Exchanger </b>
 *
 * @author Jingyi.Yang
 * @date 2016/9/20
 * Device Dell
 **/
@Log4j
public class Consumer implements Runnable {
    private List<String> buffer;
    private Exchanger<List<String>> exchanger;

    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            log.info("Consumer: Cycle " + cycle);

            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < 10; j++) {
                String buf = buffer.get(j);
                //buffer.remove(0);
                log.info("Consumer msg: " + buf);
            }

            // need to clean buffer after exchange
            buffer.removeIf(s -> true);
            cycle++;
        }
    }
}
