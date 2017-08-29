package java8.factory;

import lombok.Data;

/**
 * Usage: <b> Apple for Java 8 lambda </b>
 *
 * @author lucifer
 *         Date 2017-4-3
 *         Device Aurora R5
 */
@Data
public class Apple {
    private String color;
    private Integer weight;

    public Apple(String color, Integer weight) {
        this.color = color;
        this.weight = weight;
    }
}
