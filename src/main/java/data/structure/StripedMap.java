package data.structure;

import javax.annotation.concurrent.ThreadSafe;
import java.util.Random;

/**
 * Usage: <b>基于散列的Map, 使用分段锁维持线程安全</b>
 *
 * @user lucifer
 * @date 2016-8-31
 * @device Aurora R5
 */
@ThreadSafe
public class StripedMap {
    private static final int N_LOCKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    private static class Node {
        private Object key;
        private Object value;
        private Node next;
    }

    public StripedMap(int numBuckets) {
        buckets = new Node[numBuckets];
        locks = new Object[N_LOCKS];
        for (int i = 0; i < N_LOCKS; i++) {
            locks[i] = new Object();
        }
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node n = buckets[hash]; n != null; n = n.next) {
                if (n.key.equals(key)) {
                    return n.value;
                }
            }
        }
        return null;
    }

    public void set(Object key, Object value) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node n = buckets[hash]; ; n = n.next) {
                if (n == null) {
                    n = new Node();
                    n.key = key;
                    n.value = value;
                    buckets[hash] = n;
                    return;
                } else if (n.key.equals(key)) {
                    n.value = value;
                    return;
                } else if (n.next == null) {
                    Node m = new Node();
                    m.key = key;
                    m.value = value;
                    n.next = m;
                    return;
                }
            }
        }
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i]) {
                buckets[i] = null;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final StripedMap map = new StripedMap(10);
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.set(new Random().nextInt(15), "value set by thread " + finalI);
                }
            }).start();
        }

        System.out.println(map);
    }
}
