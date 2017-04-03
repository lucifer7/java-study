package java8.chap1;

import com.google.common.collect.Lists;
import common.entity.Apple;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Usage: <b> Filtering apples by method reference and interface </b>
 *
 * @author lucifer
 *         Date 2017-4-3
 *         Device Aurora R5
 */
public class FilterApplesMain {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple("green", 80),
                new Apple("green", 155),
                new Apple("red", 120));

        List<Apple> greenApples = filterApples(inventory, FilterApplesMain::isGreenApple);
        System.out.println(greenApples);

        List<Apple> heavyApples = filterApples(inventory, FilterApplesMain::isHeavyApple);
        System.out.println(heavyApples);

        List<Apple> greenApple2 = filterApples(inventory, (Apple a) -> "green".equalsIgnoreCase(a.getColor()));
        System.out.println(greenApple2);
    }


    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
        List<Apple> result = Lists.newArrayList();
        for (Apple a : inventory) {
            if (p.test(a)) {
                result.add(a);
            }
        }
        return result;
    }

    public static boolean isGreenApple(Apple a) {
        return "green".equalsIgnoreCase(a.getColor());
    }

    private static boolean isHeavyApple(Apple apple) {
        return apple != null && apple.getWeight() > 120;
    }
}
