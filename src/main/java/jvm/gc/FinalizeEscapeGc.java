package jvm.gc;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> 对象在GC时的自救，但此机会只有一次 </b>
 * 对象的finalize() 方法最多只被系统调用一次
 *
 * VM args: -XX:+PrintGC
 *
 * @author lucifer
 *         Date 2016-10-23
 *         Device Aurora R5
 */
@Log4j
public class FinalizeEscapeGc {
    public static FinalizeEscapeGc SAVE_HOCK = null;

    public void isAlive() {
        log.info("Object is still alive.");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        log.info("Finalize method executed...");
        FinalizeEscapeGc.SAVE_HOCK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOCK = new FinalizeEscapeGc();

        SAVE_HOCK = null;
        System.gc();
        TimeUnit.SECONDS.sleep(5);

        if (SAVE_HOCK != null) {
            SAVE_HOCK.isAlive();
        } else {
            log.info("Object is dead.");
        }

        // Copy previous code
        SAVE_HOCK = null;
        System.gc();
        TimeUnit.SECONDS.sleep(5);

        if (SAVE_HOCK != null) {
            SAVE_HOCK.isAlive();
        } else {
            log.info("Object is dead.");
        }
    }
}
