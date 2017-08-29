package util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Usage: <b> Data parse and covert Utils </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/7/4
 **/
public final class DataUtil {

    private static final char SPLITTER = ',';

    /**
     * Split string to list of Long by splitter
     *
     * @param source source string
     * @param regex  separator regex
     * @return list of Long
     */
    public static List<Long> splitString2Long(String source, String regex) {
        if (StringUtils.isBlank(source) || StringUtils.isBlank(regex)) return Collections.emptyList();
        return Stream.of(source.split(regex)).map(Long::parseLong).collect(toList());
    }

    /**
     * Split string to list of Long by comma
     *
     * @param source source string
     * @return list of Long
     */
    public static List<Long> splitString2Long(String source) {
        return splitString2Long(source, String.valueOf(SPLITTER));
    }

    /**
     * Get the nearest value(lower if equal distance) from a range of float values
     *
     * @param values range array
     * @param key    key value
     * @return nearest match
     */
    public static Float getNearestValue(Float[] values, float key) {
        Float result;
        Arrays.sort(values);
        int index = Arrays.binarySearch(values, key);
        if (index < 0) {
            index = -index - 1;
        }
        if (index == 0) {
            result = values[index];
        } else if (index == values.length) {
            result = values[index - 1];
        } else {
            result = Math.abs(key - values[index - 1]) > Math.abs(key - values[index + 1])
                    ? values[index + 1] : values[index - 1];
        }
        return result;
    }

    /**
     * Convert string to byte array, use "UTF-8" if possible
     *
     * @param src source string
     * @return parsed byte array
     */
    public static byte[] str2byte(CharSequence src) {
        if (src == null) return null;
        try {
            return src.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return src.toString().getBytes();
        }
    }
}
