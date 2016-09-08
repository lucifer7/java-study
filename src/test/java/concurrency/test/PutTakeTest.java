package concurrency.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Usage: <b>使用 Producer-Consumer to test BoundedBuffer</b>
 *
 * @user lucifer
 * @date 2016-9-8
 * @device Aurora R5
 */
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);

    // TODO: 2016-9-8 tbc

}
