package concurrency.ch3SyncTools.cyclicBarrier;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Usage: <b> 搜索程序，类似 Map, 分布式计算 </b>
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
@Log4j
public class Searcher implements Runnable {
    private int firstRow;
    private int lastRow;
    private MatrixMock matrix;
    private Results results;    // search result
    private int number;     // number to be searcher

    private final CyclicBarrier barrier;

    public Searcher(CyclicBarrier barrier, int number, Results results, MatrixMock matrix,
                    int firstRow, int lastRow) {
        this.barrier = barrier;
        this.number = number;
        this.results = results;
        this.matrix = matrix;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
    }

    @Override
    /* 查找数字 */
    public void run() {
        int counter;
        log.info(String.format("%s: Processing lines between %d and %d.",
                Thread.currentThread().getName(),
                firstRow, lastRow));

        for (int i = firstRow; i < lastRow; i++) {
            int row[] = matrix.getRow(i);
            counter = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] == number) {
                    counter++;
                }
            }
            results.setData(i, counter);    // 第 i 行共计找到 counter 处相同数字
        }

        log.info(String.format("%s: Lines processed.", Thread.currentThread().getName()));

        try {
            barrier.await();        // 执行完成，调用 await 等待
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
