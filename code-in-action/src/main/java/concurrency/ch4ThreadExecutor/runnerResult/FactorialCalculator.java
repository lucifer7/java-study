package concurrency.ch4ThreadExecutor.runnerResult;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> 一个可以返回结果并能抛出异常的阶乘计算器 </b>
 *
 * @author lucifer
 *         Date 2016-10-13
 *         Device Aurora R5
 */
@Log4j
public class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int result = 1;

        if (number == 0 || number == 1) {
            result = 1;
        } else {
            for (int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        log.info(String.format("%s: %d.", Thread.currentThread().getName(), result));
        return result;
    }
}
