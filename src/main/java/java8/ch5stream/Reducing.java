package java8.ch5stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Usage: <b> Reducing stream result (归约) </b>
 * With initial value and accumulator
 *
 * @author Jingyi.Yang
 *         Date 2017/5/4
 **/
public class Reducing {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(4, 6, 8, 4, 6);
        int sum = numbers.stream().reduce(0, (i, j) -> i + j);
        System.out.println(sum);

        int sum2 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum2);

        int max = numbers.stream().reduce(0, (i, j) -> Integer.max(i, j));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        System.out.println(min);

        int calories = DishMenu.gen().stream().map(Dish::getCalories)
                .reduce(0, Integer::sum);
        System.out.println(calories);
    }
}
