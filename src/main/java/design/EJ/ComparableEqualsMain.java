package design.EJ;

import lombok.extern.log4j.Log4j;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Usage: <b>Item 12, Comparable.compareTo are similar like .equals, but not same AWAYS </b>
 * compareTo: natural ordering
 *
 * @author lucifer
 *         Date 2017-1-15
 *         Device Aurora R5
 */
@Log4j
public class ComparableEqualsMain {
    public static void main(String[] args) {
        // same numerical value, different scales
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("1.00");

        log.info(a.equals(b));
        log.info(a.compareTo(b));

        Set hashSet = new HashSet<>();      // HashSet based on .equals()
        hashSet.add(a);
        hashSet.add(b);
        log.info("HashSet size: " + hashSet.size());
        Set treeSet = new TreeSet<>();      // TreeSet based on .compareTo()
        treeSet.add(a);
        treeSet.add(b);
        log.info("TreeSet size: " + treeSet.size());

        Double i1 = new Double("0");
        Double i2 = new Double("-0");
        log.info(i1.equals(i2));
        log.info(i1.compareTo(i2));
        log.info(i1.doubleValue() == i2.doubleValue());
    }
}
