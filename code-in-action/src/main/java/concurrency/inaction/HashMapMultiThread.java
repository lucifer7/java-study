package concurrency.inaction;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Usage: <b> HashMap may be corrupt when accessed by multiple threads </b>
 * May produces java.lang.ClassCastException: java.util.HashMap$Node cannot be cast to java.util.HashMap$TreeNode when iterate:
 * a thread tries to treeify a node while the other holds normal node
 *
 * @author lucifer
 *         Date 2017-8-29
 *         Device Aurora R5
 */
public class HashMapMultiThread {
    static Map<String, String> map = Maps.newHashMap();

    public static class AddThread implements Runnable {
        int start = 0;

        public AddThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            for (int i = start; i < 100_000; i += 2) {
                map.put(Integer.toString(i), Integer.toBinaryString(i));
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread t1 = new Thread(new HashMapMultiThread.AddThread(0));
            Thread t2 = new Thread(new HashMapMultiThread.AddThread(1));

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            System.out.println(map.size());
        }
    }
}
