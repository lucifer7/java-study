package concurrency.test;

import java.util.concurrent.CyclicBarrier;

/**
 * Usage: <b> A timer based on Barrier </b>
 *
 * @author lucifer
 *         Date 2016-10-18
 *         Device Aurora R5
 */
@Deprecated
public class BarrierTimer implements Runnable {
    private boolean started;
    private long startTime, endTime;

    @Override
    public synchronized void run() {
        long t = System.nanoTime();
        if (!started) {
            started = true;
            startTime = t;
        } else {
            endTime = t;
        }
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }

    /*private BarrierTimer barrierTimer = new BarrierTimer();
    private CyclicBarrier barrier;

    public BarrierTimer(int npairs) {
        this.barrier = new CyclicBarrier(npairs * 2 + 1, barrierTimer);
    }*/

}
