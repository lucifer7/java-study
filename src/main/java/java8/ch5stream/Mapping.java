package java8.ch5stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Usage: <b> Map and flatMap </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/2
 **/
public class Mapping {
    public static void main(String[] args) {
        List<Dish> menu = DishMenu.gen();
        List<String> dishNames = menu.stream()
                .map(Dish::getName)
                .distinct()
                .collect(toList());
        System.out.println(dishNames);

        List<Integer> wordLengths = Stream.of("Hello", "world", "!")
                .map(String::length)
                .collect(toList());
        System.out.println(wordLengths);

        // Flat map
        Stream.of("Hello", "world", "!")
                .flatMap((String line) -> Arrays.stream(line.split("")))
                .distinct()
                .forEach(System.out::println);

        // int pair matrix
        List<Integer> numbers1 = Arrays.asList(1, 4, 7, 4, 9);
        List<Integer> numbers2 = Arrays.asList(3, 4, 6);
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream().map((Integer j) -> new int[]{i, j}))
                .filter(pair -> ((pair[0] + pair[1]) % 3 == 0))
                .distinct()
                .collect(toList());
        pairs.forEach(ints -> System.out.println(Arrays.toString(ints)));
    }
}
