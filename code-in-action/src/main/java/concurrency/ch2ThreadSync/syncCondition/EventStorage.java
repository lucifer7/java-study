package concurrency.ch2ThreadSync.syncCondition;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lucifer on 2016-7-20.
 */
public class EventStorage {
    private int maxSize;
    private Deque<Date> storage;

    public EventStorage() {
        this.maxSize = 20;
        storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        storage.offer(new Date());
        System.out.printf("Set: %d\n", storage.size());

        notifyAll();
    }

    public synchronized Date get() {
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Date date = storage.poll();
        System.out.printf("Get: %s, after size: %d\n", date, storage.size());
        notifyAll();
        return date;
    }
}
