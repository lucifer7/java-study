package java8.ch4stream;

import java8.factory.Dish;
import java8.factory.DishMenu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Usage: <b> A basic stream demo of filter, map, collect </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/12
 **/
public class BasicStream {

    private static final int CALORIC_LIMIT = 400;

    public static void main(String[] args) {
        List<Dish> dishes = DishMenu.gen();
        getLowCalDishesByCycle(dishes).forEach(System.out::println);

        System.out.println("======");

        getLowCalDishesByStream(dishes).forEach(System.out::println);
    }

    /**
     * Get low caloric dish names by for cycle, before java 8
     * @param dishes dish list
     * @return list of dish name
     */
    private static List<String> getLowCalDishesByCycle(List<Dish> dishes) {
        List<Dish> lowCalDishes = new ArrayList<>();
        for (Dish d : dishes) {
            if (d.getCalories() < CALORIC_LIMIT) {
                lowCalDishes.add(d);
            }
        }

        lowCalDishes.sort(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        List<String> result = new ArrayList<>(lowCalDishes.size());
        for (Dish d : lowCalDishes) {
            result.add(d.getName());
        }
        return result;
    }

    /**
     * Get low caloric dish names by stream, in java 8
     * @param dishes dish list
     * @return list of dish name
     */
    private static List<String> getLowCalDishesByStream(List<Dish> dishes) {
        return dishes.stream()
                .filter(d -> d.getCalories() < CALORIC_LIMIT)
                .map(Dish::getName)
                .collect(Collectors.toList());
    }
}
