package data.structure.collection;

import java.util.Map;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-3-8
 *         Device Aurora R5
 */
public class MapEntry<K, V> implements Map.Entry<K,V> {
    private K key;
    private V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V result = this.value;
        this.value = value;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MapEntry)) return false;

        MapEntry<?, ?> mapEntry = (MapEntry<?, ?>) o;

        if (!key.equals(mapEntry.key)) return false;
        return value.equals(mapEntry.value);
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }
}
