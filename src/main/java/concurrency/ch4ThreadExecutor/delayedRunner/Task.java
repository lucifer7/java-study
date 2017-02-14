package concurrency.ch4ThreadExecutor.delayedRunner;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/14
 **/
@Slf4j
class Task implements Callable<String> {
    private String name;

    Task(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        log.info("{}: starting at {}.", this.name, new Date());
        return "Hello World.";
    }
}
