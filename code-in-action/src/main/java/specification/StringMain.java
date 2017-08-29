package specification;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-12-8
 *         Device Aurora R5
 */
@Log4j
public class StringMain {
    public static void main(String[] args) {
        String s = System.currentTimeMillis() + " now";
        //s.intern();
        // intern on compile time constant is unnecessary
        log.info(("a" + "b" + "c").intern() == "abc");

        int[][] array = new int[4][5];
        log.info(array.getClass());

        String s1 = new String("abc");
        String s2 = new String("abc");
        log.info(s1 == s2);     // false
        log.info(s1.intern() == s2.intern());       // true

        String s3 = "bcd";
        String s4 = "bcd";
        log.info(s3 == s4);     // true
    }
}
