package concurrency.thread.group;

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
    static ThreadGroup threadGroup = new ThreadGroup("searcher");
    static Result result = new Result();
    static SearchTask searchTask = new SearchTask(result);

    public static void main(String[] args) {
        /*Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(searchTask);
        }

        threadGroup.enumerate(threads);*/

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(threadGroup, searchTask);
            thread.start();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
