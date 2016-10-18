package jvm.oom;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Usage: <b> Test Runtime Constant Pool OutOfMemoryError </b>
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * @author lucifer
 *         Date 2016-10-16
 *         Device Aurora R5
 */
public class RuntimeConstantPoolOom {
    public static void main(String[] args) {
        List<String> list = Lists.newLinkedList();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
