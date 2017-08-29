package concurrency.sync.cache;

import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/2
 * Time: 13:40
 **/
@Log4j
public class MemorizerFutureTask<A, V> implements Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private Computable<A, V> c;

    public MemorizerFutureTask(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        Future future = cache.get(arg);
        if(future == null) {
            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask futureTask = new FutureTask(callable);

            future = futureTask;
            cache.put(arg, futureTask);
            Thread thread = new Thread(futureTask);
            thread.start();

            log.info("ARG= " + arg + " ----Loading from cache, new computable task....");
        } else {
            log.info("ARG= " + arg + " ----Loading from cache, waiting for result----");
        }

        try {
            return (V) future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;    //?
    }
}
