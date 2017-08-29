package concurrency.ch2ThreadSync.syncCondition;

/**
 * Created by lucifer on 2016-7-20.
 */
public class Producer implements Runnable {
    private EventStorage storage;

    public Producer(EventStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            storage.set();
        }
    }
}
