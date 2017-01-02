package specification;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-12-8
 *         Device Aurora R5
 */
public class StringMain {
    public static void main(String[] args) {
        String s = System.currentTimeMillis() + " now";
        s.intern();
    }
}
