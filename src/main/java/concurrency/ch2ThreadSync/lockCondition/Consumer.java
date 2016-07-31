package concurrency.ch2ThreadSync.lockCondition;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by lucifer on 2016-7-31.
 */
public class Consumer implements Runnable {
    private FileMock file;
    private Buffer buffer;

    public Consumer(FileMock file, Buffer buffer) {
        this.file = file;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (buffer.hasPendingLines()) {
            String line = buffer.get();
            processLine(line);
        }
    }

    private void processLine(String line) {
        try {
            //System.out.printf("%s processing line: %s\n", Thread.currentThread().getName(), line);
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
