package data.structure.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Usage: <b> Special type of Sets and requirements of special methods </b>
 *
 * @author lucifer
 *         Date 2017-2-25
 *         Device Aurora R5
 */
@Slf4j
public class TypesForSets {
    static <T> void fill(Set<T> set, Class<T> type) {
        try {
            for (int i = 0; i < 10; i++) {
                set.add(type.getConstructor(int.class).newInstance(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static <T> void test(Set<T> set, Class<T> type) {
        fill(set, type);
        fill(set, type);        // add duplicate items
        fill(set, type);
        //log.info(set.toString());
        System.out.println(set);
    }

    public static void main(String[] args) {
        // valid types
        test(new HashSet<HashType>(), HashType.class);
        test(new LinkedHashSet<HashType>(), HashType.class);
        test(new TreeSet<TreeType>(), TreeType.class);

        // invalid types
        log.info("invalid types");
        test(new HashSet<SetType>(), SetType.class);
        test(new HashSet<TreeType>(), TreeType.class);
        test(new LinkedHashSet<SetType>(), SetType.class);
        test(new LinkedHashSet<TreeType>(), TreeType.class);

        try {
            test(new TreeSet<SetType>(), SetType.class);
            test(new TreeSet<HashType>(), HashType.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

class SetType {
    int i;

    public SetType(int i) {
        this.i = i;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SetType && (i == ((SetType) o).i);
    }

    @Override
    public String toString() {
        return "{" +
                "i=" + i +
                '}';
    }
}

class HashType extends SetType {
    public HashType(int i) {
        super(i);
    }

    @Override
    public int hashCode() {
        return i;
    }
}

class TreeType extends SetType implements Comparable<TreeType> {
    public TreeType(int i) {
        super(i);
    }

    @Override
    public int compareTo(TreeType o) {
        return o.i < i ? -1 : o.i == i ? 0 : 1;
    }
}