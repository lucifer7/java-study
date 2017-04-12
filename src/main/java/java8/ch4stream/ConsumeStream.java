package java8.ch4stream;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Usage: <b> Unlike Collections, Stream can be consumed only once </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/12
 **/
public class ConsumeStream {
    public static void main(String[] args) {
        Stream<String> names = Stream.of("dou", "ni", "wan");
        names.forEach(System.out::println);

        // Produces Run-time exception: IllegalStateException: stream has already been operated upon or closed
        names.forEach(System.out::println);
    }
}
