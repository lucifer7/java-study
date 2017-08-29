package data.type.generalization.selfBounded;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> Self bounded and CuriouslyRecurringGeneric </b>
 * 自限定的类型之一： 古怪的循环泛型
 * CRG： 基类用导出类替代其参数，即所产生的类中将使用确切类型而非基类型
 *
 * @author lucifer
 *         Date 2017-1-23
 *         Device Aurora R5
 */
public class CRGWithBasicHolder {
    public static void main(String[] args) {
        Subtype sb1 = new Subtype(), sb2 = new Subtype();
        sb1.set(sb2);
        Subtype sb3 = sb1.get();
        sb1.f();
    }
}

@Log4j
class BasicHolder<T> {
    T element;

    void set(T arg) {
        element = arg;
    }

    T get() {
        return element;
    }

    void f() {
        log.info(element.getClass().getSimpleName());
    }
}

class Subtype extends BasicHolder<Subtype> {}