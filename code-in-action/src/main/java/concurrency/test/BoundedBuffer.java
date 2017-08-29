package concurrency.test;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.Semaphore;

/**
 * Usage: <b> 基于信号量的有界缓存 </b>
 * 并发程序的测试
 * 详见对应的单元测试
 *
 * @user lucifer
 * @date 2016-9-6
 * @device Aurora R5
 */
@ThreadSafe
public class BoundedBuffer<E> {
    private final Semaphore availableItems, availableSpaces;
    @GuardedBy("this")
    private final E[] items;
    @GuardedBy("this")
    private int putPosition = 0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E e) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(e);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E e) {
        int i = putPosition;
        items[i] = e;
        putPosition = (++i == items.length) ? 0 : i;        // 队列存满后从队首开始，覆盖旧数据
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E e = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;       // 取数据按照先进先出原则
        return e;
    }

}
