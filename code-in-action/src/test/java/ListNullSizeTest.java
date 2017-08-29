import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-9-22
 *         Device Aurora R5
 */
@Log4j
public class ListNullSizeTest {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayListWithCapacity(20);
        list.add(null);
        log.info(CollectionUtils.isEmpty(list));
        log.info(list.size());
        log.info(list);
        //log.info("hello world");
    }
}
