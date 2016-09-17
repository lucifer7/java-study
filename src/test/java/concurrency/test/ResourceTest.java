package concurrency.test;

import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

import static org.junit.Assert.assertTrue;

/**
 * Usage: <b> 资源管理的测试 </b>
 * 如果对资源管理适当，缓存使用前和装满后系统的内存用量差别不会太大
 * @author lucifer
 *         Date 2016-9-17
 *         Device Aurora R5
 */
@Log4j
public class ResourceTest {
    private static final int CAPACITY = 1000;
    private static final int THRESHOLD = 20 * 1024 * 1024;

    @Test
    public void testLeak() throws InterruptedException {
        BoundedBuffer<Big> bb = new BoundedBuffer<>(CAPACITY);
        System.gc();

        long usage1 = heapSize();
        for (int i = 0; i < CAPACITY; i++) {
            bb.put(new Big());
        }
        for (int i = 0; i < CAPACITY; i++) {
            bb.take();
        }
        System.gc();

        long usage2 = heapSize();

        assertTrue(Math.abs(usage2 - usage1) < THRESHOLD);      // 如果 BoundedBuffer 对 take()后的资源释放了，两次内存快照应当差别不大
    }

    private long heapSize() {
        MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = bean.getHeapMemoryUsage();

        log.info(String.format("Memory Initial: %d.", usage.getInit()));        // 初始内存
        log.info(String.format("Memory max: %d.", usage.getMax()));             // 最大可用内存
        log.info(String.format("Memory usage: %d.", usage.getUsed()));          // 已用内存

        return usage.getUsed();
    }

    class Big {
        double[] data = new double[100000];
    }
}
