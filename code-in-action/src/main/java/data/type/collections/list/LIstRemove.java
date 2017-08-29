package data.type.collections.list;

import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/13
 **/
@Log4j
public class LIstRemove {
    public static void main(String[] args) {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        for (String temp : a) {
            if ("4".equals(temp)) {     /* Only ok when second last item, others report ConcurrentModificationException */
                a.remove(temp);
            }
        }
        log.info(Arrays.toString(a.toArray()));
    }
}
