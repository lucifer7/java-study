package data.structure;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Usage: <b>WeakHashMap使用了弱引用，可用于缓存</b>
 * 会在各项操作中调用 expungeStaleEntries() 清理过期表项
 * @author lucifer
 * @date 2016-8-21
 * @device Yoga Pro
 */
public class WeakHashMapMain {
    public static void main(String[] args) {
        Map map = new WeakHashMap();

        for (int i = 0; i < 100000; i++) {
            Integer ii = new Integer(i);
            map.put(ii, new Byte[i]);
        }
    }

}
