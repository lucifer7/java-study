package zookeeper.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

import java.util.concurrent.TimeUnit;

import static zookeeper.original.common.ProjectProperties.ZK_ADDRESS;
import static zookeeper.original.common.ProjectProperties.ZOOKEEPER_PATH;

/**
 * Usage: <b> Global lock for distributed system. </b>
 * Generating znode: /Lock_path/_c_5f25a8a0-6807-4eeb-acfa-206012bb3617-lock-0000000007
 *
 * @author Jingyi.Yang
 *         Date 2017/8/31
 **/
@Slf4j
public class DistributedLock {
    public static void main(String[] args) {
        CuratorFramework curator = CuratorFrameworkFactory.newClient(ZK_ADDRESS, new RetryNTimes(10, 5000));
        curator.start();
        log.info("ZK cli start");

        Thread t1 = new Thread(() -> doWithLock(curator));
        Thread t2 = new Thread(() -> doWithLock(curator));

        t1.start();
        t2.start();
    }

    private static void doWithLock(CuratorFramework curator) {
        InterProcessMutex lock = new InterProcessMutex(curator, ZOOKEEPER_PATH);
        try {
            if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {
                log.info("Lock acquired by {}", Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(10);
            }
        } catch (Exception e) {
            log.error("Lock acquire failed", e);
        } finally {
            try {
                lock.release();
                log.info("Lock released by {}", Thread.currentThread().getName());
            } catch (Exception e) {
                log.error("Lock release failed", e);
            }
        }
    }
}
