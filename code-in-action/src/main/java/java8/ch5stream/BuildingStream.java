package java8.ch5stream;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Usage: <b> Build Stream by 6 ways </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/12
 **/
@Slf4j
public class BuildingStream {

    public static void main(String[] args) throws IOException {
        // 1. Stream.of
        Stream<String> names = Stream.of("dou", "ni", "wan");
        names.forEach(System.out::println);

        // 2. Empty stream
        Stream<Integer> empty = Stream.empty();
        System.out.println(empty.count());

        // 3. Array to Stream (primitive types have their correspondent stream, to avoid boxing)
        int[] intArray = {2, 3, 6, 8, 9};
        IntStream intStream = Arrays.stream(intArray);
        System.out.println(intStream.sum());

        // 4.1 Stream.iterate
        Stream.iterate(1, i -> i + 2)
                .limit(3)
                .forEach(System.out::println);

        // 4.2 fibonacci with iterate
        Stream.iterate(new int[]{0, 1}, tuple -> new int[] {tuple[0] + tuple[1], tuple[0] + tuple[1] + tuple[1]})
                .limit(4)
                //.map(tuple -> tuple[0])
                .forEach(tuple -> System.out.println(Arrays.toString(tuple)));

        // 5.1 Stream.generate stream of random double
        Stream.generate(Math::random)
                .limit(3)
                .forEach(System.out::println);

        // 5.2 Stream.generate steam of int 1
        IntStream.generate(() -> 1)
                .limit(4)
                .forEach(System.out::println);

        // 6. Get stream from file
        long uniqueWords = Files.lines(Paths.get("/tmp/vm.log"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();
        System.out.println("Unique words count: " + uniqueWords);
    }
}
