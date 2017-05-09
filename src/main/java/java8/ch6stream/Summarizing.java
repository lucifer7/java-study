package java8.ch6stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

import static java.util.stream.Collectors.counting;

/**
 * Usage: <b> Summarizing by reduction </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/5
 **/
public class Summarizing {
    private static List<Dish> menu = DishMenu.gen();

    public static void main(String[] args) {
        System.out.println("No. of dishes: " + countDishes());
        System.out.println("Most caloric dish: " + mostCaloricDish());
        System.out.println("Most caloric dish: " + mostCaloricDishByComparator());

    }

    private static Dish mostCaloricDishByComparator() {
        Comparator<Dish> dishComparator = Comparator.comparingInt(Dish::getCalories);
        BinaryOperator<Dish> dishBinaryOperator = BinaryOperator.maxBy(dishComparator);
        return menu.stream().reduce(dishBinaryOperator).get();
        //return menu.stream().collect(reducing(dishBinaryOperator)).get();
    }

    private static long countDishes() {
        return menu.stream().collect(counting());
    }

    private static Dish mostCaloricDish() {
        return menu.stream().reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2).get();
    }
}
