import lombok.extern.log4j.Log4j;

import java.util.Random;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-9-17
 *         Device Aurora R5
 */
@Log4j
public class RandomMain {
    public static void main(String ... args) {
        // System.out.println(randomString(-229985452)+' '+randomString(-147909649));

        int i = 42;
        while (i-->0) {
            log.info(i);
        }
    }

    public static String randomString(int seed) {
        Random rand = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for(int i=0;;i++) {
            int n = rand.nextInt(27);
            if (n == 0) break;
            sb.append((char) ('`' + n));
        }
        return sb.toString();
    }
}
