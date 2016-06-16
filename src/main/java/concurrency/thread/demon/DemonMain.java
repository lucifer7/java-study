package concurrency.thread.demon;

import entity.Event;
import lombok.extern.log4j.Log4j;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/16
 * Time: 14:19
 **/
@Log4j
public class DemonMain {
    public static void main(String[] args) {
        //@NotThreadSafe
        //Deque<Event> deque = new ArrayDeque<>();    // 20 - 30
        Deque<Event> deque = new ConcurrentLinkedDeque<>();     //remain 30 - 27

        WriterTask writer = new WriterTask(deque);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(writer);
            thread.start();
        }

        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
    }
}
