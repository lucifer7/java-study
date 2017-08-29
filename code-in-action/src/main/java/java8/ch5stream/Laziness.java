package java8.ch5stream;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Usage: <b> Lazy computation </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/5/2
 **/
public class Laziness {
    public static void main(String[] args) {
        Stream.of(1, 21, 5, 6, 4, 3, 5, 9)
                .filter(integer -> {
                    System.out.println("Filtering " + integer);
                    return integer % 2 == 0;
                })
                .map(integer -> {
                    System.out.println("Squaring " + integer);
                    return integer * integer;
                })
                .limit(2)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
