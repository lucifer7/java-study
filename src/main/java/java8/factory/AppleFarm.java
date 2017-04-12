package java8.factory;

import com.google.common.collect.Lists;
import org.apache.commons.lang.math.RandomUtils;

import java.util.List;

/**
 * Usage: <b> Generate apples for java 8  </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/12
 **/
public class AppleFarm {
    private static final int WEIGHT_LIMIT = 500;
    private static String[] COLOR = new String[] {"green", "red", "yellow", "brown"};

    public static List<Apple> genList(int capacity) {
        List<Apple> apples = Lists.newArrayListWithCapacity(capacity);
        for (int i = 0; i < capacity; i++) {
            apples.add(new Apple(COLOR[RandomUtils.nextInt(COLOR.length)], RandomUtils.nextInt(WEIGHT_LIMIT)));
        }
        return apples;
    }
}
