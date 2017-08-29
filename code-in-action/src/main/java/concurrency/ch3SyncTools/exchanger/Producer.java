package concurrency.ch3SyncTools.exchanger;

import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Usage: <b> Producer for Exchanger </b>
 *
 * @author Jingyi.Yang
 * @date 2016/9/20
 * Device Dell
 **/
@Log4j
public class Producer implements Runnable {
    private List<String> buffer;        // Buffer, wait to be exchanged
    private Exchanger<List<String>> exchanger;

    public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        int cycle = 1;

        // 1. Generate data
        for (int i = 0; i < 10; i++) {
            log.info("Producer: Cycle " + cycle);

            for (int j = 0; j < 10; j++) {
                String msg = "Event " + (i * 10 + j);
                log.info("Producer: " + msg);
                buffer.add(msg);
            }

            // 2. Exchange data
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cycle++;
        }
    }
}
