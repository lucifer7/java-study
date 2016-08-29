package concurrency.executor.simpleThreadpool;

/**
 * Usage: <b>线程池中永不退出的线程</b>
 *
 * @author lucifer
 * @date 2016-8-29
 * @devide Yoga Pro
 */
public class PermThread extends Thread {
    private ThreadPool pool;            // 线程池
    private Runnable target;            // 目标任务
    private boolean isShutDown = false; // 是否关闭
    private boolean isIdle = false;     // 是否空闲

    public PermThread(String name, Runnable target, ThreadPool pool) {
        super(name);
        this.target = target;
        this.pool = pool;
    }

    public synchronized void setTarget(Runnable target) {
        this.target = target;
        notifyAll();
    }

    public Runnable getTarget() {
        return target;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void run() {
        while (!isShutDown) {
            isIdle =  false;
            if (target != null) {
                target.run();
            }

            isIdle = true;
            pool.repool(this);
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isIdle = false;
        }
    }

    public synchronized void shutDown() {
        isShutDown = true;
        notifyAll();
    }
}
