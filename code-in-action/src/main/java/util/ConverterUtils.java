package util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;
import java.util.function.Function;

/**
 * Usage: <b> Convert util helper for pre Java 8 </b>
 *
 * @author lucifer
 *         Date 2017-6-16
 *         Device Yoga Pro
 */
public class ConverterUtils {
    public static <K, V> Map<K, List<V>> group(List<V> source, Function<V, K> convert) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyMap();
        }

        Map<K, List<V>> map = Maps.newHashMap();
        for (V v : source) {
            K k = convert.apply(v);
            List<V> values = map.get(k);
            if (CollectionUtils.isEmpty(values)) {
                values = Lists.newArrayList();
            }
            values.add(v);
            map.put(k, values);
        }
        return map;
    }

    public static <T, R> List<R> map(Collection<T> source, Function<T, R> convert) {
        if (CollectionUtils.isEmpty(source)) {
            return Collections.emptyList();
        }

        List<R> list = Lists.newArrayListWithCapacity(source.size());
        for (T t : source) {
            list.add(convert.apply(t));
        }
        return list;
    }
    
    private static class GroupUtils {
        private static <K, V, E> Map<E, List<V>> group(Map<K, V> source, Function<V, E> convert) {
            if (MapUtils.isEmpty(source)) {
                return Collections.emptyMap();
            }
            Map<E, List<V>> map = new HashMap<>();
            for (Map.Entry<K, V> kvEntry : source.entrySet()) {
                K key = kvEntry.getKey();
                V val = kvEntry.getValue();
                // try catch maybe
                E e = convert.apply(val);
                if (map.containsKey(e)) {
                    map.get(e).add(val);
                } else {
                    List<V> newList = Lists.newArrayList(val);
                    map.put(e, newList);
                }
            }
            return map;
        }
    }

}
