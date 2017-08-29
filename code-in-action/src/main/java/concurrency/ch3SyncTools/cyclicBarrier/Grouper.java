package concurrency.ch3SyncTools.cyclicBarrier;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> 统一结果，类似 Reduce, 对 Searcher 的结果进行统计处理 </b>
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
@Log4j
public class Grouper implements Runnable {
    private Results results;

    public Grouper(Results results) {
        this.results = results;
    }

    @Override
    /* 统一结果，计算次数的总和 */
    public void run() {
        int finalResult = 0;
        log.info("Grouper: Processing results...");

        int data[] = results.getData();
        for (int i : data) {
            finalResult += i;
        }

        log.info("Grouper: Total result is: " + finalResult);
    }
}
