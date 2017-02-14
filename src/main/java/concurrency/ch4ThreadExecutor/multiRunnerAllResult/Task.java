package concurrency.ch4ThreadExecutor.multiRunnerAllResult;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/14
 **/
@Slf4j
class Task implements Callable<Result> {
    private String name;

    public Task(String name) {
        this.name = name;
    }

    @Override
    public Result call() throws Exception {
        log.info("{}: starting...", this.name);

        try {
            long duration = RandomUtils.nextInt(10);
            log.info("{}: waiting {} ms for result.", this.name, duration);
            TimeUnit.MILLISECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int value = 0;
        for (int i = 0; i < 5; i++) {
            value += (int) (Math.random() * 100);
        }

        Result result = new Result();
        result.setName(this.name);
        result.setValue(value);

        log.info("{}: Ends.", this.name);

        return result;
    }
}
