package concurrency.test;

import java.util.BitSet;
import java.util.concurrent.CountDownLatch;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/6/8
 **/
public class BitSetMain {
    public static void main(String[] args) throws Exception {
        BitSet bs = new BitSet();
        CountDownLatch latch = new CountDownLatch(1);
        Thread t1 = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(1000);
            } catch (Exception ex) {
                System.out.println(ex);
            }
            bs.set(1);
        });
        Thread t2 = new Thread(() -> {
            try {
                latch.await();
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            bs.set(2);
        });

        t1.start();
        t2.start();
        latch.countDown();
        t1.join();
        t2.join();
        // crucial part here:
        System.out.println(bs.get(1));
        System.out.println(bs.get(2));
    }
}
