package concurrency.ch1ThreadMng.thread.demon;

import common.entity.Event;
import lombok.extern.log4j.Log4j;

import java.util.Date;
import java.util.Deque;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/16
 * Time: 14:06
 **/
@Log4j
public class CleanerTask extends Thread {
    private Deque<Event> deque;

    public CleanerTask(Deque<Event> deque) {
        this.deque = deque;
        setDaemon(true);
    }

    @Override
    public void run() {
        while(true) {
            Date date = new Date();
            clean(date);
        }
    }

    private void clean(Date date) {
        if(deque.size() < 1) {
            return;
        }

        long difference;
        boolean isDelete = false;

        do {
            Event event = deque.getLast();
            difference = date.getTime() - event.getDate().getTime();
            if(difference > 10*1000) {
                log.info("Cleaner execute, " + event.getEvent());
                deque.removeLast();
                isDelete = true;
            }
        } while (difference > 10*1000);

        if(isDelete) {
            log.info("After cleaner: " + deque.size());
        }

    }
}
