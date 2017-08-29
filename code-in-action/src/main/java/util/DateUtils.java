package util;

import java.util.Calendar;

/**
 * Usage: <b> Utility for date parser </b>
 *
 * @author Jingyi.Yang
 *         Date 2016/9/20
 **/
public final class DateUtils {
    public static int getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }
}
