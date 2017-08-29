package specification.ch4types;

import lombok.extern.slf4j.Slf4j;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/3/6
 **/
@Slf4j
public class LongEqualMain {
    public static void main(String[] args) {
        Long a = 123L;
        Long b = 130L;
        Long c = (a + b) * 10;
        Long d = 10 * a + 10 * b;
        Long e = a + 2;
        Long f = b - 5;

        System.out.println(a == 123);
        System.out.println(c == 2530);      // for constant, == true
        System.out.println(c == d);         // for variable, == compares reference
        System.out.println(c.equals(d));    // for variable, equals compares value
        System.out.println(e == f);         // for long less than 127, autoboxing
    }
}
