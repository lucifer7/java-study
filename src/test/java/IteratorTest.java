import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 * @since 2016/9/1
 **/
@Log4j
public class IteratorTest {
    @Test
    public void iterator() {
        List<String> list = Lists.newArrayListWithCapacity(10);

        for (int i = 0; i < 10; i++) {
            list.add(System.currentTimeMillis() + "");
        }

        Iterator ite = list.iterator();
        while (ite.hasNext()) {
            String s = (String) ite.next();
            ite.remove();
        }

        log.info(list);
    }
}
