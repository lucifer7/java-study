package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Usage: <b> Provider formatter for Double, Float </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/14
 **/
public class Formatter {
    /**
     * Use DecimalFormat to format double and float
     */
    private static DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    static {
        df.setMaximumFractionDigits(340);       // set to upper limit DOUBLE_FRACTION_DIGITS
    }

    /**
     * Convert double to plain string, without exponential and trailing zeros,
     * and support more than 6 decimals (of String.format("%f/g"))
     * @param d double obj
     * @return converted string
     */
    public static String toPlainString(Double d) {
        return df.format(d);
    }

    // not sure of decimal precision
    public static String toPlainString(Float f) {
        return df.format(new BigDecimal(f).doubleValue());
    }
}
