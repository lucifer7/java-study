package data.type.generalization;

import lombok.extern.log4j.Log4j;

/**
 * 3. Usage: 针对单独方法的泛型
 * Created by Lucifer
 * Date: 2016-8-15
 * Device: Aurora R5
 */
@Log4j
public class GenericMethod {
    public <K, V> void method(K k, V v) {
        log.info(k.getClass().getSimpleName());
        log.info(v.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        GenericMethod gm = new GenericMethod();
        gm.method(new String("generic"), new Long(4));
    }
}
