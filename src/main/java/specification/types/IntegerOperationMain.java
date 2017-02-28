package specification.types;

/**
 * Usage: <b> ArithmeticException, overflow  </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/27
 **/
public class IntegerOperationMain {
    public static void main(String[] args) {
        int i = 1000000;
        System.out.println(i * i);                  // overflow
        long l = i;
        System.out.println(l * l);
        System.out.println(20296 / (l - i));        // ArithmeticException
    }
}
