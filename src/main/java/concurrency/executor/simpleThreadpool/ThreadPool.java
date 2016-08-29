package concurrency.executor.simpleThreadpool;

import java.util.List;
import java.util.Vector;

/**
 * Usage: <b>线程的实现</b>
 *
 * @author lucifer
 * @date 2016-8-29
 * @devide Yoga Pro
 */
public class ThreadPool {
    private static volatile ThreadPool instance = null;

    private List<PermThread> idleThreads;
    private int threadCount;
    private boolean isShutDown = false;

    private ThreadPool(int poolSize) {
        this.idleThreads = new Vector<>(poolSize);
        threadCount = 0;
    }

    public int getThreadCount() {
        return threadCount;
    }

    // 工厂方法产生新实例
    public static ThreadPool getInstance(int poolSize) {
        if (instance == null) {
            synchronized (ThreadPool.class) {
                if (instance == null)
                    instance = new ThreadPool(poolSize);
            }
        }
        return instance;
    }

    // 线程放入池中，如果关闭，则关闭线程
    protected synchronized void repool(PermThread permThread) {
        if (!isShutDown) {
            idleThreads.add(permThread);
        } else {
            permThread.shutDown();
        }
    }

    // 关闭线程池，关闭其中所有线程
    public synchronized void shutDown() {
        isShutDown = true;
        for (int i = 0; i < idleThreads.size(); i++) {
            idleThreads.get(i).shutDown();
        }
    }

    public synchronized void start(Runnable target) {
        if (idleThreads.size() > 0) {
            PermThread permThread = idleThreads.get(idleThreads.size() - 1);
            idleThreads.remove(permThread);
            permThread.setTarget(target);
        } else {
            PermThread permThread = new PermThread("PermThread #" + ++threadCount, target, this);
            permThread.start();
        }
    }
}
