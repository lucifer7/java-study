package java8.ch6stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * Usage: <b> Grouping data  </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/4
 **/
public class Grouping {
    private enum CaloricLevel {DIET, NORMAL, FAT}

    private static List<Dish> menu = DishMenu.gen();
    private static Map<String, List<String>> dishTags = DishMenu.dishTags();

    public static void main(String[] args) {
        System.out.println("Dishes grouped by type: " + groupDishesByType());
        System.out.println("Dish names grouped by type: " + groupDishNamesByType());
        System.out.println("Dish tags grouped by type: " + groupDishTagsByType());
        
        System.out.println("Caloric dishes grouped by type: " + groupCaloricDishesByType());
        System.out.println("Dishes grouped by caloric level: " + groupDishesByCaloricLevel());
        System.out.println("Dishes grouped by type and caloric level: " + groupDishesByTypeAnaCaloricLevel());

        System.out.println("Count dishes in group: " + countDishesInGroups());
        System.out.println("Most caloric dish by type: " + mostCaloricDishByType());
        System.out.println("Most caloric dish by type without optional: " + mostCaloricDishByTypeWithoutOptional());

        System.out.println("Sum calories by type: " + sumCaloriesByType());
        System.out.println("Caloric levels by type: " + caloricLevelsByType());
    }

    private static Map<Dish.Type, List<Dish>> groupDishesByType() {
        return menu.stream().collect(groupingBy(Dish::getType));
    }

    private static Map<Dish.Type, List<String>> groupDishNamesByType() {
        return menu.stream().collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
    }

    private static Map<Dish.Type, Set<String>> groupDishTagsByType() {
        //return menu.stream().collect(groupingBy(Dish::getType, flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet())));         // error
        return null;
    }

    private static Map<Dish.Type, List<Dish>> groupCaloricDishesByType() {
        return menu.stream().filter(dish -> dish.getCalories() > 500).collect(groupingBy(Dish::getType));
        //return menu.stream().collect(groupingBy(Dish.Type, filtering(dish -> dish.getCalories() > 500, toList())));                           // error
    }

    private static CaloricLevel getCaloricLevel(Dish dish) {
        if (dish.getCalories() <= 400) {
            return CaloricLevel.DIET;
        } else if (dish.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
        } else {
            return CaloricLevel.FAT;
        }
    }

    private static Map<CaloricLevel, List<Dish>> groupDishesByCaloricLevel() {
        return menu.stream().collect(groupingBy(Grouping::getCaloricLevel));
    }

    private static Map<Dish.Type, Map<CaloricLevel, List<Dish>>> groupDishesByTypeAnaCaloricLevel() {
        return menu.stream().collect(groupingBy(Dish::getType, groupingBy(
                Grouping::getCaloricLevel
        )));
    }

    private static Map<Dish.Type, Long> countDishesInGroups() {
        return menu.stream().collect(groupingBy(Dish::getType, counting()));
    }

    private static Map<Dish.Type, Optional<Dish>> mostCaloricDishByType() {
        //return menu.stream().collect(groupingBy(Dish::getType, maxBy((Dish i, Dish j) -> Long.compare(i.getCalories(), j.getCalories()))));       // wrong
        return menu.stream().collect(groupingBy(Dish::getType, reducing((Dish d1, Dish d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)));
    }

    private static Map<Dish.Type, Dish> mostCaloricDishByTypeWithoutOptional() {
        /*return menu.stream().collect(groupingBy(Dish::getType,
                collectingAndThen(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2),
                        Optional::get)));*/     // using grouping
        return menu.stream().collect(Collectors.toMap(Dish::getType, Function.identity(), (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
    }

    private static Map<Dish.Type, Long> sumCaloriesByType() {
        return menu.stream().collect(groupingBy(Dish::getType, summingLong(Dish::getCalories)));
    }

    private static Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType() {
        return menu.stream().collect(groupingBy(Dish::getType, mapping(Grouping::getCaloricLevel, toSet())));
    }


}
