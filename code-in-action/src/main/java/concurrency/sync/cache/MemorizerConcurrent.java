package concurrency.sync.cache;

import lombok.extern.log4j.Log4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/6/2
 * Time: 11:31
 **/
@Log4j
public class MemorizerConcurrent<A, V> implements Computable<A, V> {
    /*
    Improve MemorizerSync on concurrency
     */
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public MemorizerConcurrent(Computable<A, V> c) {
        this.c = c;
    }

    //TODO: fix synchronized error
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if(null == result) {
            result = c.compute(arg);
            cache.put(arg, result);
        } else {
            log.info(" Loading from concurrent cache..");
        }
        /*cache.putIfAbsent(arg, c.compute(arg));*/
        return result;
    }
}
