package data.type.generalization.Array;

import lombok.extern.log4j.Log4j;

import java.lang.reflect.Array;

/** FINAL
 * Usage: <b>A BETTER DESIGN</b>
 * 使用类型标识
 *
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
 */
@Log4j
public class GenericArrayWithTypeToken<T> {
    private T[] array;

    @SuppressWarnings("unchecked")
    public GenericArrayWithTypeToken(Class<T> type, int size) {
        array = (T[]) Array.newInstance(type, size);
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gti = new GenericArrayWithTypeToken<>(Integer.class, 20);
        gti.put(0, 10086);
        log.info(gti.get(0));
        Integer[] array = gti.rep();
        log.info(array[0]);
    }
}
