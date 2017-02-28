package specification.types;

import lombok.extern.slf4j.Slf4j;

/**
 * Usage: <b> Zero in floating point type </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/27
 **/
@Slf4j
public class FloatingPointTypeMain {
    public static void main(String[] args) {
        System.out.println(0f / 0f);                    // NaN(Not-a-number)
        System.out.println(Float.NaN == Double.NaN);    // static NaN for Float and Double

        System.out.println(0.0 == -0.0);                // Positive zero and negtive zero compare equal
        System.out.println(0.0 > -0.0);
        System.out.println(2.0 / 0.0);                  // But sometimes they are different, Infinity and -Infinity
        System.out.println(1.0 / -0.0);

        log.info("NaN is unordered.  Float.NaN > 0 {}, Float.NaN == Float.NaN {}, Float.NaN != Float.NaN {}.",
                Float.NaN > 0, Float.NaN == Float.NaN, Float.NaN != Float.NaN);
        Float x = Float.NaN, y = 0f;
        log.info("In particular, x < y == !(x >=y) will always be true, but when one of them is NaN: {}", x < y == !(x >=y));
    }
}
