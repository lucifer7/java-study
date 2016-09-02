package data.structure;

import lombok.extern.log4j.Log4j;

import java.util.*;

/**
 * Usage: <b> 测试Map接口的实现类 </b>
 *
 * @author lucifer
 * @date 2016-8-16
 * @device Yoga Pro
 */
@Log4j
public class MapMain {
    public static void main(String[] args) {
        /* HashTable do not allow null value or null key */
        Map hashTable = new Hashtable();
        //hashTable.put(null, "null");    //java.lang.NullPointerException
        //hashTable.put("null", null);    //java.lang.NullPointerException
        //hashTable.put(null, null);      //java.lang.NullPointerException

        /* HashMap allow null value and null key */
        Map hashMap = new HashMap();
        hashMap.put(null, "null");
        hashMap.put("null", null);
        hashMap.put(null, null);
        //log.info("Value now is: " + hashMap.get(null));
        _printMapValue(hashMap);

        /* HashMap is not thread sage, 乱序 */

        /* LinkedHashMap: 有序的HashMap. 两种顺序：插入顺序，最近访问的顺序 */
        Map insertOrderLink = new LinkedHashMap();
        insertOrderLink.put("123", 123);
        insertOrderLink.put("456", 456);
        insertOrderLink.put("789", 789);
        _printMapValue(insertOrderLink);

        Map timeOrderLink = new LinkedHashMap(16, 0.75f, true);
        timeOrderLink.put("1", 1);
        timeOrderLink.put("2", 2);
        timeOrderLink.put("3", 3);
        _printMapValue(timeOrderLink);

        /* TreeMap 提供了排序，按key，实现了 NavigableMap(SortedMap) 接口 */

    }

    private static void _printMapValue(Map map) {
        for(Iterator iterator = map.keySet().iterator(); iterator.hasNext(); ) {
            log.info("Map value is " + map.get(iterator.next()));
        }
    }
}
