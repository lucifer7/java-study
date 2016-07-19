package concurrency.ch1ThreadMng.thread.group;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/20
 * Time: 15:58
 **/
@Log4j
public class GroupMain {
    private static ThreadGroup threadGroup = new ThreadGroup("searcher");
    private static Result result = new Result();
    private static SearchTask searchTask = new SearchTask(result);

    public static void main(String[] args) {
        // INITIAL THREAD AND GROUP
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // CHECK THREAD GROUP INFO
        log.info("Active count: " + threadGroup.activeCount());
        log.info("Active Group Count: " + threadGroup.activeGroupCount());
        threadGroup.list();

        // THREAD ENUMERATE
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        for (Thread thread : threads) {
            log.info(thread.getName() + " present state: " + thread.getState());
        }
        String name = "€";

        // INTERRUPT THREADS
        //threadGroup.interrupt();

        // CHECK RESULT WHEN FINISH
        if(waitFinish(threadGroup)) {
            log.error("Result is: " + result.getName());    //last thread name
        }

    }

    private static boolean waitFinish(ThreadGroup threadGroup) {
        while(threadGroup.activeCount() > 0) {
            try {
                log.info("Present result: " + result.getName());
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
