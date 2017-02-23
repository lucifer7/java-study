package jvm;

/**
 * Usage: <b> </b>
 *
 * VM args:
 * See default: -XX:+PrintGC -XX:+PrintGCDetails
 * -Xms20m -Xmx20m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+PrintGC -XX:+PrintGCDetails
 *
 * @author Jingyi.Yang
 *         Date 2017/2/23
 **/
public class HeapRatio {
    public static void main(String[] args) {
        System.out.println("hello world.");

        // Keep thread running, no log output
        //while (true) {}
    }
}
