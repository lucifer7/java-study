package java8.ch5stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/3
 **/
public class NumericStream {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(4, 6, 8, 4, 6);
        Arrays.stream(numbers.toArray()).sorted().forEach(System.out::println);

        List<Dish> menu = DishMenu.gen();
        int calories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        System.out.println("Number of calories: " + calories);

        OptionalInt maxCalories = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        maxCalories.ifPresent(i -> System.out.println("Max calories is " + i));

        IntStream evenNumbers = IntStream.rangeClosed(1, 200)
                .filter(i -> i % 2 == 0);
        System.out.println(evenNumbers.count());

        Stream<int[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap((Integer i) -> IntStream.rangeClosed(i, 100)
                        .filter(j -> Math.sqrt(i * i + j * j) % 1 == 0)
                        .boxed()
                        .map(j -> new int[]{i, j, (int) Math.sqrt(i * i + j * j)}));
        pythagoreanTriples.forEach(t -> System.out.println(Arrays.toString(t)));
    }
}
