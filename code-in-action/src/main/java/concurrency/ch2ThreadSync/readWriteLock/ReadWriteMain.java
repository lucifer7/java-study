package concurrency.ch2ThreadSync.readWriteLock;

/**
 * Created by lucifer on 2016-7-24.
 */
public class ReadWriteMain {
    public static void main(String[] args) {
        PricesInfo pricesInfo = new PricesInfo();
        Reader reader = new Reader(pricesInfo);
        Writer writer = new Writer(pricesInfo);

        Thread thread1 = new Thread(reader, "Reader-Thread");
        Thread thread2 = new Thread(writer, "Writer-Thread");

        thread1.start();
        thread2.start();
    }
}
