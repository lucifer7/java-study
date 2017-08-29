package java8.ch6stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * Usage: <b> Partitioning by predicate </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/5
 **/
public class Partitioning {
    private static List<Dish> menu = DishMenu.gen();

    public static void main(String[] args) {
        System.out.println("Dishes partitioned by vegetarian: " + partitionByVegetarian());
        System.out.println("Vegetarian dishes by type: " + vegetarianDishesByType());
        System.out.println("Most caloric dishes by vegetarian: " + mostCaloricDishesByVegetarian());
    }

    private static Map<Boolean, List<Dish>> partitionByVegetarian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian));
    }

    private static Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));
    }

    private static Object mostCaloricDishesByVegetarian() {
        return menu.stream().collect(partitioningBy(Dish::isVegetarian,
                collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
    }
}
