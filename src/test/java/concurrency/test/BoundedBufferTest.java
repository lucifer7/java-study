package concurrency.test;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Usage: <b>并发程序的测试，对于有界队列 BoundedBuffer</b>
 *
 * @user lucifer
 * @date 2016-9-8
 * @device Aurora R5
 */
@Log4j
public class BoundedBufferTest {
    /* 基本操作和属性的测试 */
    @Test
    public void isEmpty() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    public void isFull() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            try {
                bb.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(bb.isEmpty());
        assertTrue(bb.isFull());
    }

    @Test
    public void isEmptyAfterClean() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            try {
                bb.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            try {
                bb.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertFalse(bb.isFull());
        assertTrue(bb.isEmpty());
    }

    /* 对阻塞操作的测试 */
    @Test
    public void takeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread taker = new Thread() {
            public void run() {
                try {
                    int u = bb.take();
                    fail("Should not reach here.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            taker.start();
            Thread.sleep(200);
            taker.interrupt();
            taker.join(200);
            assertFalse(taker.isAlive());
        } catch (InterruptedException e) {
            fail("Should not throw exception.");
        }
    }

}