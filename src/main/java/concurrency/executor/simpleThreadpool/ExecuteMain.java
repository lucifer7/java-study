package concurrency.executor.simpleThreadpool;

import lombok.Synchronized;
import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 * @date 2016-8-29
 * @devide Yoga Pro
 */
@Log4j
public class ExecuteMain {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            new Thread(new MyTask("MyTask_NO." + i)).start();
        }
        log.info("Total for normal threads: " + (System.currentTimeMillis() - start));


        start = System.currentTimeMillis();
        ThreadPool threadPool = ThreadPool.getInstance(10);
        for (int i = 0; i < 1000; i++) {
            threadPool.start(new MyTask("MyTask_NO." + i));
        }
        log.info("Total for thread pool: " + (System.currentTimeMillis() - start));

        // Result is 100244 and 62, 感觉不科学啊
        // 78 and 107, using thread start, 感觉还是没卵用啊
        // TODO: 2016-8-29 考虑试下JDK自带的Executor框架，对线程池再做优化，可能是内部锁用得太丧心病狂了
    }
}
