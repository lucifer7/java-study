package data.type.generalization.Array;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b>泛型数组无法直接创建，但可以定义引用</b>
 * <b>成功创建泛型数组的唯一方式：创建一个类型擦除的数组，然后转型</b>
 *
 * @author lucifer
 * @date 2016-8-19
 * @devide Yoga Pro
 */
@Log4j
public class GenericArray {
    public static final int SIZE = 100;
    private static Generic<Integer>[] gia;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //gia = (Generic<Integer>[]) new Object[SIZE];        // Cause java.lang.ClassCastException
        gia = (Generic<Integer>[]) new Generic[SIZE];
        log.info(gia.getClass().getSimpleName());               // Generic[], Runtime type is raw type(erased)

        gia[0] = new Generic<Integer>();
        log.info(gia[0].getClass().getSimpleName());        // Generic
        Generic<Integer> g = gia[0];
        log.info(g.getClass().getSimpleName());             // Generic

        /*for (int i = 0; i < SIZE; i++) {
            gia[i] = new Generic<>();
            log.info(gia[i].toString());
        }*/
    }
}
