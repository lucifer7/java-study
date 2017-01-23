package data.type.generalization.selfBounded;

/**
 * Usage: <b> BasicHolder may accept any class as its generic param </b>
 *
 * @author lucifer
 *         Date 2017-1-23
 *         Device Aurora R5
 */
public class Unconstrained {
    public static void main(String[] args) {
        BasicOther bo1 = new BasicOther(), bo2 = new BasicOther();
        bo1.set(new Other());
        Other other = bo1.get();
        bo1.f();
    }
}

class Other {}

class BasicOther extends BasicHolder<Other> {}
