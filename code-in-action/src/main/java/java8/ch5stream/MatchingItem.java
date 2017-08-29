package java8.ch5stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.List;
import java.util.Optional;

/**
 * Usage: <b> Test if stream matches any, none, all, or find first, any </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/2
 **/
public class MatchingItem {
    public static void main(String[] args) {
        List<Dish> menu = DishMenu.gen();

        if ( isVegetarianFriendlyMenu(menu)) {
            System.out.println("Vegetarian friendly.");
        } else {
            System.out.println("Not vegetarian friendly.");
        }

        System.out.println(isHealthyMenu(menu));
        System.out.println(noUnhealthyMenu(menu));

        Optional<Dish> anyVegetarianDish = anyVegetarianDish(menu);
        anyVegetarianDish.ifPresent(dish -> System.out.println(dish.getName()));

        Optional<Dish> firstVegeterianDish = fistVegetarianDish(menu);
        firstVegeterianDish.ifPresent(dish -> System.out.println(dish.getName()));
    }

    private static boolean isVegetarianFriendlyMenu(List<Dish> menu) {
        return menu.stream().allMatch(Dish::isVegetarian);
    }

    private static boolean isHealthyMenu(List<Dish> menu) {
        return menu.stream().allMatch(dish -> dish.getCalories() < 1000);
    }

    private static boolean noUnhealthyMenu(List<Dish> menu) {
        return menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);
    }

    private static Optional<Dish> anyVegetarianDish(List<Dish> menu) {
        return menu.stream().filter(Dish::isVegetarian).findAny();
    }

    private static Optional<Dish> fistVegetarianDish(List<Dish> menu) {
        return menu.stream().filter(Dish::isVegetarian).findFirst();
    }
}
