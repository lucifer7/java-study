package data.type.generalization.latentTyping;

import generics.SimpleQueue;
import generics.coffee.Coffee;
import generics.coffee.Latte;
import generics.coffee.Mocha;
import lombok.extern.log4j.Log4j;
import net.mindview.util.Generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-2-8
 *         Device Aurora R5
 */
@Log4j
public class AdapterLatent {
    public static void main(String[] args) {
        // 1. Adapt a Collection
        List<Coffee> carrier = new ArrayList<>();
        Fill.fill(new AddableCollectionAdapter<>(carrier), Coffee.class, 3);

        // 2. Helper method captures the type
        Fill.fill(Adapter.collectionAdapter(carrier), Latte.class, 2);
        for (Coffee c : carrier) {
            log.info(c);
        }

        // 3. Use an adapted class
        AddableSimpleQueue<Coffee> coffeeQueue = new AddableSimpleQueue<>();
        Fill.fill(coffeeQueue, Mocha.class, 4);
        Fill.fill(coffeeQueue, Latte.class,2);
        for (Coffee c : coffeeQueue) {
            log.info(c);
        }
    }
}

interface Addable<T> { void add(T t); }

class Fill {
    // Fill with Class token
    static <T> void fill(Addable<T> addable, Class<? extends T> classToken, int size) {
        for (int i = 0; i < size; i++) {
            try {
                addable.add(classToken.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    // Fill with Generator
    static <T> void fill(Addable<T> addable, Generator<T> generator, int size) {
        for (int i = 0; i < size; i++) {
            addable.add(generator.next());
        }
    }
}

// Make any Collection addable using composition
class AddableCollectionAdapter<T> implements Addable<T> {
    private Collection<T> c;

    public AddableCollectionAdapter(Collection<T> c) {
        this.c = c;
    }

    @Override
    public void add(T t) {
        c.add(t);
    }
}

// A helper to capture the type automatically
class Adapter {
    static <T> Addable<T> collectionAdapter(Collection<T> c) {
        return new AddableCollectionAdapter<T>(c);
    }
}

// Make a SimpleQueue addable using inheritance
class AddableSimpleQueue<T> extends SimpleQueue<T> implements Addable<T> {
    @Override
    public void add(T t) {
        super.add(t);
    }
}