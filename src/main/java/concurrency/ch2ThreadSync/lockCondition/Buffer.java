package concurrency.ch2ThreadSync.lockCondition;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lucifer on 2016-7-31.
 */
public class Buffer {
    private LinkedList<String> buffer;      //存储共享数据
    private int maxSize;                    //max size for buffer
    private ReentrantLock lock;
    private Condition lines;
    private Condition space;
    private boolean pendingLines;           //等待新行？

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
        buffer = new LinkedList<>();
        lock = new ReentrantLock();
        lines = lock.newCondition();
        space = lock.newCondition();
        pendingLines = true;
    }

    public void insert(String line) {
        lock.lock();
        try {
            while (buffer.size() == maxSize) {
                //wait();   //java.lang.IllegalMonitorStateException ?
                space.await();
            }
            buffer.offer(line);
            System.out.printf("%s insert line at: %d\n", Thread.currentThread().getName(), buffer.size());
            lines.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String get() {
        String line = null;
        lock.lock();

        try {
            //while (buffer.size() == 0 && hasPendingLines()) {
            while (buffer.size() == 0 && pendingLines) {
                lines.await();      //release lock before waiting, and re-acquire lock after await returns
            }
            if (hasPendingLines()) {
                line = buffer.poll();
            }
            System.out.printf("%s read a line at %d: %s\n", Thread.currentThread().getName(), buffer.size(), line);
            space.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return line;
    }

    public void setPendingLines(boolean pendingLines) {
        this.pendingLines = pendingLines;
    }

    public boolean hasPendingLines() {
        return pendingLines || buffer.size() > 0;
    }
}
