package concurrency.ch1ThreadMng.thread.local;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/20
 * Time: 13:51
 **/
// 不安全的本地线程变量
@Log4j
public class UnsafeTask implements Runnable {
    private Date startDate;

    @Override
    public void run() {
        startDate = new Date();
        log.info(Thread.currentThread().getName() + " starts at: " + startDate);

        try {
            TimeUnit.MILLISECONDS.sleep(RandomUtils.nextLong(1000, 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Finish " + Thread.currentThread().getName() + " with startDate " + startDate);
    }
}
