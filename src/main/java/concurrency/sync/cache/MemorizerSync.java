package concurrency.sync.cache;

import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangjingyi on 2016-6-1.
 */
@Log4j
public class MemorizerSync<A, V> implements Computable<A, V> {
    //@GuardedBy("this")
    private final Map<A, V> cache = new HashMap<A, V>();
    private final Computable<A, V> c;

    public MemorizerSync(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        } else {
            log.info("Loading from cache..");
        }
        return result;
    }
}
