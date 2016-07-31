package concurrency.ch2ThreadSync.lockCondition;

/**
 * Created by lucifer on 2016-7-31.
 */
public class Producer implements Runnable {
    private FileMock file;
    private Buffer buffer;

    public Producer(FileMock file, Buffer buffer) {
        this.file = file;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        buffer.setPendingLines(true);

        while (file.hasMoreLines()) {
            String line = file.getLine();
            buffer.insert(line);
        }

        buffer.setPendingLines(false);
    }
}
