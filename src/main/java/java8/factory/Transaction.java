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

    private final int id;
    private final Trader trader;
    private final int year;
    private final int value;
    //private final String type;
}
