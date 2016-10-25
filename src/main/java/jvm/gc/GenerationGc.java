package jvm.gc;

import static common.ProjectContants._1MB;

/**
 * Usage: <b>  </b>
 * On Java 8
 *
 * VM args: -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8
 *
 * Failed on java 8
 * VM args: -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
 *
 * Failed on java 8
 * VM args: -XX:+PrintGCDetails -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15
 *
 * @author lucifer
 *         Date 2016-10-25
 *         Device Aurora R5
 */
public class GenerationGc {
    // 1. Test Minor GC on Yong Generation
    public static void testMinorGc() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    // 2. Huge Object allocate into Old Space directly, once reach threshold
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 3. long live objects move into Tenure Space
    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        // 1. Minor for young
        //testMinorGc();

        // 2. Huge objects into old
        //testPretenureSizeThreshold();

        // 3. long live objects into tenure
        testTenuringThreshold();
    }
}
