package data.type.generalization;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b>使用模板方法，创建泛型的实例</b>
 *
 * @author lucifer
 * @date 2016-8-19
 * @devide Yoga Pro
 */
@Log4j
public class GenericCreator {
    public static void main(String[] args) {
        new Creator().f();

        GenericWithCreate<X> creator = new Creator();
        X x = creator.create();
        log.info(x);
    }
}

abstract class GenericWithCreate<T> {
    final T element;

    public GenericWithCreate() {
        this.element = create();
    }

    abstract T create();
}

class X {}

@Log4j
class Creator extends GenericWithCreate<X> {

    @Override
    X create() {
        return new X();
    }

    void f() {
        log.info(element.getClass().getSimpleName());
    }
}
