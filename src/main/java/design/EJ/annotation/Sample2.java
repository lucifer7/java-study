package design.EJ.annotation;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-3-5
 *         Device Aurora R5
 */
public class Sample2 {
    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int i = 0;
        i = i / i;
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m2() {
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest(ArithmeticException.class)
    public static void m3() {
    }
}
