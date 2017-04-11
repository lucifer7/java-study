package specification.ch5conversions;

/**
 * Usage: <b> 6 contexts </b>
 * 1. Cast contexts
 * 2. String contexts
 * 3. assignment contexts
 * 4. numeric promotion(widening) ?
 * 5. strict invocation contexts ?
 * 6. loose invocation contexts ?
 *
 * @author Jingyi.Yang
 *         Date 2017/4/11
 **/
public class ConversionContexts {
    public static void main(String[] args) {
        // Casting conversion (5.4) of a float literal to
        // type int. Without the cast operator, this would
        // be a compile-time error, because this is a
        // narrowing conversion (5.1.3):
        int i = (int)12.5f;

        // String conversion (5.4) of i's int value:
        System.out.println("(int)12.5f==" + i);

        // Assignment conversion (5.2) of i's value to type
        // float. This is a widening conversion (5.1.2):
        float f = i;

        // String conversion of f's float value:
        System.out.println("after float widening: " + f);

        // Numeric promotion (5.6) of i's value to type
        // float. This is a binary numeric promotion.
        // After promotion, the operation is float*float:
        System.out.print(f);
        f = f * i;

        // Two string conversions of i and f:
        System.out.println("*" + i + "==" + f);

        // Invocation conversion (5.3) of f's value
        // to type double, needed because the method Math.sin
        // accepts only a double argument:
        double d = Math.sin(f);

        // Two string conversions of f and d:
        System.out.println("Math.sin(" + f + ")==" + d);

        char c = 'i';
        short ch = (short) c;
        System.out.println(ch);

        int i1 = 0XFFFF;
        System.out.println(i1);

        // bitwise XOR lowest priority, copies the bit if set in one operand but not both
        int i2 = 15 * 16 ^ 3 + 15 * 16 ^ 2 + 15 * 16 ^ 1 + 15 * 16;
        System.out.println(i2);


        int big = 1234567890;
        float approx = big;
        System.out.println(big - (int)approx);
    }

}
