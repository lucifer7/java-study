package concurrency.ch1ThreadMng.thread.local;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/20
 * Time: 13:56
 **/
public class VariableMain {
    public static void main(String[] args) {
        //unsafeLocalVariable();

        safeLocalVariable();
    }

    private static void safeLocalVariable() {
        SafeTask task = new SafeTask();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void unsafeLocalVariable() {
        UnsafeTask task = new UnsafeTask();

        for (int i = 0; i < 10; i++) {
            //Thread thread = new Thread(new UnsafeTask());       // safe with local variable
            Thread thread = new Thread(task);       // 不安全的本地线程变量
            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
