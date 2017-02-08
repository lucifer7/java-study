package data.type.generalization.latentTyping;

import net.mindview.util.Generator;

import java.util.Collection;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-2-8
 *         Device Aurora R5
 */
public class AdapterLatent {
}

interface Addable<T> { void add(T t); }

class Filee {
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

    static <T> void fill(Addable<T> addable, Generator<T> generator, int size) {
        for (int i = 0; i < size; i++) {
            addable.add(generator.next());
        }
    }
}

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

class Adapter {
    static <T> Addable<T> collectionAdapter(Collection<T> c) {
        return new AddableCollectionAdapter<T>(c);
    }
}
