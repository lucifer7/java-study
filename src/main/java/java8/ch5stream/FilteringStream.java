package java8.ch5stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Usage: <b> Filter stream by predicate, distinct, limit, skip </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/14
 **/
public class FilteringStream {
    public static void main(String[] args) {
        List<Dish> menu = DishMenu.gen();

        // 1. Filter with predicate
        List<Dish> vegetarianMenu = menu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());
        vegetarianMenu.forEach(System.out::println);


        // 2. Filter unique odd number
        Stream.of(1, 2, 5, 8, 2, 3, 4)
                .filter(integer -> integer % 2 == 0)
                .distinct()
                .forEach(System.out::println);

        // 3. Truncate stream with limit
        menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // 4. Skip elements
        menu.stream()
                .sorted(Comparator.comparing(Dish::getCalories).reversed())
                .skip(6)
                .forEach(System.out::println);
    }
}
