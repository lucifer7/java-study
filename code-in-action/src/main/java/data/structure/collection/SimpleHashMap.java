package data.structure.collection;

import net.mindview.util.Countries;

import java.util.*;

/**
 * Usage: <b> A simple implementation of HashMap, in TJ </b>
 * Slot/bucket of the hashMap, index is calculated from key
 *
 * @author lucifer
 *         Date 2017-3-10
 *         Device Aurora R5
 */
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {
    static final int SIZE = 998;    // in old time, they suggest use prime number, now it matters little
    LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

    @Override
    public V put(K key, V value) {
        V oldValue = null;
        int index = Math.abs(key.hashCode()) % SIZE;

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        LinkedList<MapEntry<K, V>> bucket = buckets[index];

        MapEntry<K, V> pair = new MapEntry<K, V>(key, value);
        boolean found = false;
        ListIterator<MapEntry<K, V>> li = bucket.listIterator();

        while (li.hasNext()) {
            MapEntry<K, V> iPair = li.next();
            if (iPair.getKey().equals(key)) {
                oldValue = iPair.getValue();
                li.set(pair);       // not set ipair.value ?
                found = true;
                break;
            }
        }

        if (!found) {
            buckets[index].add(pair);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        LinkedList<MapEntry<K, V>> bucket = buckets[index];
        if (bucket == null) return null;

        ListIterator<MapEntry<K, V>> li = bucket.listIterator();
        while (li.hasNext()) {
            MapEntry<K, V> iPair = li.next();
            if (iPair.getKey().equals(key)) {
                return iPair.getValue();
            }
        }
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (LinkedList<MapEntry<K, V>> bucket : buckets) {
            if (bucket != null) {
                set.addAll(bucket);     // add each element ?
            }
        }

        return set;
    }

    public static void main(String[] args) {
        SimpleHashMap<String, String> map = new SimpleHashMap<>();
        map.putAll(Countries.capitals(25));
        System.out.println(map);
        System.out.println(map.get("COMOROS"));
        System.out.println(map.entrySet());
    }
}
