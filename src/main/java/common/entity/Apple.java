package common.entity;

import lombok.Data;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-4-3
 *         Device Aurora R5
 */
@Data
public class Apple {
    private String color;
    private int weight;

    public Apple(String color, int weight) {
        this.color = color;
        this.weight = weight;
    }
}
