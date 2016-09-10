package concurrency.ch3SyncTools.cyclicBarrier;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.CyclicBarrier;

/**
 * Usage: <b> CyclicBarrier 测试类 </b>
 * 相比 CountDownLatch, 这货有两点好处：
 * 1. 能重复使用
 * 2. 能传递一个 Runnable，在全部分线程计算完成后调用，可以用来对结果统一处理
 * 所以用途也有点区别：
 * 1. CountDownLatch 一次性使用，在 application/module 启动时使用
 * 2. CyclicBarrier 并行计算，并在数据集更新后可以重新计算
 *
 * 与 join() 的区别：
 * t.join(): current Thread waiting for t to finish
 * each thread have a reference to another thread
 * makes code dirty and hard to maintain
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
@Log4j
public class CyclicMain {
    public static void main(String[] args) {
        final int ROWS = 10000;
        final int NUMBERS = 1000;
        final int SEARCH = 5;
        final int PARTICIPANTS = 5;
        final int LINES_PARTICIPANTS = 2000;

        MatrixMock matrix = new MatrixMock(ROWS, NUMBERS, SEARCH);
        //Results results = new Results(PARTICIPANTS);
        Results results = new Results(ROWS);    // results 将有  10000 元素
        Grouper grouper = new Grouper(results);
        CyclicBarrier barrier = new CyclicBarrier(PARTICIPANTS, grouper);

        for (int i = 0; i < PARTICIPANTS; i++) {
            Searcher searcher = new Searcher(barrier, SEARCH, results, matrix,
                    LINES_PARTICIPANTS * i, LINES_PARTICIPANTS * (i + 1));
            Thread thread = new Thread(searcher, "Searcher-" + i);
            thread.start();
        }

        log.info("Main thread finishes job.");
    }
}
