package specification.ch5conversions;

/**
 * Usage: <b> Unary numeric promotion is performed in the following situations: </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/18
 **/
public class UnaryNumericPromotion {
    public static void main(String[] args) {
        // 1. dimension expression promotion of array creation expression
        byte b = 2;
        int a[] = new int[b];

        // 2. index expression promotion of array access expression
        char c = '\u0001';
        a[c] = 1;

        // 3. unary minus operator - promotion [or unary plus operator +]
        a[0] = -c;
        System.out.println("a: " + a[0] + "," + a[1]);
        b = -1;

        // 4. bitwise complement operator ~ promotion
        int i = ~b;
        System.out.println("~0x" + Integer.toHexString(b)
                + "==0x" + Integer.toHexString(i));

        // 5. shift promotion [<<, >>, >>>] (left operand)
        i = b << 4L;
        System.out.println("0x" + Integer.toHexString(b)
                + "<<4L==0x" + Integer.toHexString(i));
    }
}
