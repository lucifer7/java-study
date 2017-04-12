package java8.factory;

import lombok.Data;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/12
 **/
@Data
public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public enum Type {MEAT, FISH, OTHER}
}
