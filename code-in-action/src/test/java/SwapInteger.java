import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: Jingyi.Yang
 * Date: 2016/7/4
 * Time: 10:18
 **/
@Log4j
public class SwapInteger {

    private void swap(Integer a, Integer b) {
        a = a + b;
        b = a - b;
        a = a - b;
    }

    private void swap2(Integer a, Integer b) {
        Integer t = a;
        a = b;
        b = t;
    }

    private void swap33(AtomicInteger a, AtomicInteger b) {
        a.set(b.getAndSet(a.get()));
    }

    private Integer dummy(Integer i, Integer dummy) {
        return i;
    }

    @Test
    public void test() {
        Integer a = 1, b = 2;
        a = dummy(b, b = a);
        log.info("a = " + a + ", b = " + b);

        AtomicInteger i = new AtomicInteger(1);
        AtomicInteger j = new AtomicInteger(2);
        swap33(i, j);
        log.info("i = " + i + ", j = " + j);
    }
}
