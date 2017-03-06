package jvm.oom;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Usage: <b> Test Heap OutOfMemoryError </b>
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGC
 *
 * @author lucifer
 *         Date 2016-10-9
 *         Device Aurora R5
 */
public class HeapOom {
    static class OomObject {
    }

    public static void main(String[] args) {
        List<OomObject> list = Lists.newLinkedList();

        while (true) {
            list.add(new OomObject());
        }
    }
}