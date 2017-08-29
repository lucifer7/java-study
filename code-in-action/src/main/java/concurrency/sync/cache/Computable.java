package concurrency.sync.cache;

/**
 * Created by yangjingyi on 2016-6-1.
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
