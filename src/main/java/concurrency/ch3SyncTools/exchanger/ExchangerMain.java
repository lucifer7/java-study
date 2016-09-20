package concurrency.ch3SyncTools.exchanger;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * Usage: <b> Exchanger 工具类，在并发任务间交换数据 </b>
 * 适用于生产者-消费者模式，但只能同步两个线程
 *
 * Consumer do exchange first, and clean the buffer after reading the data
 *
 * <p>
 * 进阶版可以用 SynchronousQueue
 *
 * @author Jingyi.Yang
 *         Date 2016/9/20
 **/
@Log4j
public class ExchangerMain {
    public static void main(String[] args) {
        List<String> buffer = Lists.newArrayList();
        List<String> buffer2 = Lists.newArrayList();
        Exchanger<List<String>> exchanger = new Exchanger<>();

        Thread producer = new Thread(new Producer(buffer, exchanger));
        Thread consumer = new Thread(new Consumer(buffer2, exchanger));
        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("---Exchanger complete---");
    }
}
