package java8.ch6stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

/**
 * Usage: <b> Write your own Collector by implementing interface </b>
 *
 * @author lucifer
 *         Date 2017-6-8
 *         Device Aurora R5
 */
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {
    /* Create the start of Collector */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /* Iterate collection and accumulate to supplier */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /* Combine the result of two accumulators */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    /* Final operation on accumulator */
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }
}
