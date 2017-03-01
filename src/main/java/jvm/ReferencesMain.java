package jvm;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.*;
import java.util.LinkedList;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-3-1
 *         Device Aurora R5
 */
@Slf4j
public class ReferencesMain {
    private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<>();

    public static void checkQueue() {
        Reference<? extends VeryBig> inq = rq.poll();
        if (null != inq) {
            log.info("In queue: {}", inq.get());
        }
    }

    public static void main(String[] args) {
        int size = 10;
        if (args.length > 0) {
            size = new Integer(args[0]);
        }

        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            sa.add(new SoftReference<>(new VeryBig("Soft-" + i), rq));
            log.info("Just created: {}", sa.getLast());
            checkQueue();
        }

        LinkedList<WeakReference<VeryBig>> wa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            wa.add(new WeakReference<>(new VeryBig("Weak-" + i), rq));
            log.info("Just created: {}", wa.getLast());
            checkQueue();
        }

        SoftReference<VeryBig> s = new SoftReference<VeryBig>(new VeryBig("Soft"));
        WeakReference<VeryBig> w = new WeakReference<VeryBig>(new VeryBig("Weak"));

        System.gc();

        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            pa.add(new PhantomReference<>(new VeryBig("Phantom-" + i), rq));
            log.info("Just created: {}", pa.getLast());
            checkQueue();
        }
    }
}

@Slf4j
class VeryBig {
    private static final int SIZE = 10000;
    private long[] la = new long[SIZE];
    private String ident;

    public VeryBig(String ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return "VeryBig{" +
                "ident='" + ident + '\'' +
                '}';
    }

    @Override
    protected void finalize() throws Throwable {
        //super.finalize();
        log.info("Finalizing {}.", ident);
    }
}
