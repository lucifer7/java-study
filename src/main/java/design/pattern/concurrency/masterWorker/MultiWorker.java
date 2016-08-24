package design.pattern.concurrency.masterWorker;

import java.util.Random;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 * @date 2016-8-24
 * @devide Yoga Pro
 */
public class MultiWorker extends Worker {
    @Override
    public Object handle(Object input) {
        Integer integer = (Integer) input;
        Integer multiplier = new Random().nextInt(5);
        for (int i = 0; i < multiplier; i++) {
            integer *= integer;
        }
        return integer;
    }
}
