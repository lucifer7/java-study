package java8.factory;

import lombok.Data;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/10
 **/
@Data
public class Transaction {
    public static final String GROCERY = "Grocery";

    private int id;
    private Trader trader;
    private int year;
    private int value;
    private String type;
}
