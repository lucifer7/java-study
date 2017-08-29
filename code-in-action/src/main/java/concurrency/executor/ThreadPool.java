package concurrency.executor;

import concurrency.sync.cache.Computable;
import concurrency.sync.cache.ExpensiveFunction;
import lombok.extern.log4j.Log4j;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/21
 * Time: 15:37
 **/
@Log4j
public class ThreadPool {
    private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(20);
    private static int corePoolSize = 4;
    private static int maximumPoolSize = 4;
    private static long keepAliveTime = 100;

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

    public static void main(String[] args) {
        /*for (int i = 0; i < 5; i++) {
            Runnable runnable = new SafeTask();
            executor.execute(runnable);
        }*/
        for (int i = 0; i < 5; i++) {
            final Computable caculator = new ExpensiveFunction();
            Callable c = new Callable() {
                @Override
                public Object call() throws Exception {
                    return caculator.compute("doubi");
                }
            };
            Future futureTask = executor.submit(c);
            try {
                log.info("No. " + i + " result = " + futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        //executor.shutdownNow();   //interrupt all threads
        executor.shutdown();


    }

}
