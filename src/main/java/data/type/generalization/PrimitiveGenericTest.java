package data.type.generalization;


import lombok.extern.log4j.Log4j;
import net.mindview.util.Generator;
import net.mindview.util.RandomGenerator;

/**
 * Usage: <b> Any primitive type cannot be generic param </b>
 * Which is the weakness of auto-boxing mechanism
 *
 * @author lucifer
 *         Date 2017-1-17
 *         Device Aurora R5
 */
@Log4j
public class PrimitiveGenericTest {
    public static void main(String[] args) {
        String[] strings = FArray.fill(new String[7], new RandomGenerator.String(10));
        for (String s : strings) {
            log.info(s);
        }

        Integer[] integers = FArray.fill(new Integer[7], new RandomGenerator.Integer());
        for (Integer i : integers) {
            log.info(i);
        }

        // reason: no instance(s) of type variable(s) T exist so that int[] conforms to T[]
        //int[] b = FArray.fill(new int[7], new RandomGenerator.Integer());
    }
}

class FArray {
    public static <T> T[] fill(T[] a, Generator<T> generator) {
        for (int i = 0; i < a.length; i++) {
            a[i] = generator.next();
        }
        return a;
    }
}
