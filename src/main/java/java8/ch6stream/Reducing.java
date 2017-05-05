package java8.ch6stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.List;

import static java.util.stream.Collectors.reducing;

/**
 * Usage: <b> Reducing accumulator, with multiple implementations </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/5
 **/
public class Reducing {
    private static List<Dish> menu = DishMenu.gen();

    public static void main(String[] args) {
        System.out.println("Total calories in menu: " + calculateTotalCalories());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithMethodRef());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesWithoutCollectors());
        System.out.println("Total calories in menu: " + calculateTotalCaloriesUsingSum());
    }

    private static int calculateTotalCalories() {
        return menu.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
    }

    private static int calculateTotalCaloriesWithMethodRef() {
        return menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));
    }

    private static int calculateTotalCaloriesWithoutCollectors() {
        return menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
    }

    private static int calculateTotalCaloriesUsingSum() {
        return menu.stream().mapToInt(Dish::getCalories).sum();
    }
}
