package data.structure.collection;

import containers.MapEntry;
import net.mindview.util.Countries;

import java.util.*;

/**
 * Usage: <b> SlowMap implementation in TJ </b>
 *
 * @author lucifer
 *         Date 2017-3-8
 *         Device Aurora R5
 */
public class SlowMap<K, V> extends AbstractMap<K, V> {
    private List<K> keys = new ArrayList<K>();
    private List<V> values = new ArrayList<V>();

    @Override
    public V put(K key, V value) {
        V oldValue = get(key);
        if (!keys.contains(key)) {
            keys.add(key);
            values.add(value);
        } else {
            values.set(keys.indexOf(key), value);
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        if (!keys.contains(key)) {
            return null;
        } else {
            return values.get(keys.indexOf(key));
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>(keys.size());
        Iterator<K> ki = keys.iterator();
        Iterator<V> vi = values.iterator();

        while (ki.hasNext()) {
            set.add(new MapEntry<K, V>(ki.next(), vi.next()));
        }
        return set;
    }

    public static void main(String[] args) {
        SlowMap<String, String> map = new SlowMap<>();
        map.putAll(Countries.capitals(15));
        System.out.println(map);
        System.out.println(map.get("ALGERIA"));
        System.out.println(map.entrySet());
    }
}
