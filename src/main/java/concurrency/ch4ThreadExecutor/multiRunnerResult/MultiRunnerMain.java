package concurrency.ch4ThreadExecutor.multiRunnerResult;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2016-10-24
 *         Device Aurora R5
 */
@Log4j
public class MultiRunnerMain {
    public static void main(String[] args) {
        String name = "yang";
        UserValidator validator = new UserValidator(name);

        Callable<Boolean> task1 = () -> {
            boolean r = validator.validate(name, "pwd");
            if (r) return r;
            else throw new Exception("Error password!");
        };
        Callable<Boolean> task2 = () -> {
            boolean r = validator.validate(name, "psd");
            if (r) return r;
            else throw new Exception("Error password!.");
        };

        List<Callable<Boolean>> tasks = Lists.newArrayList();
        tasks.add(task1);
        tasks.add(task2);

        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            boolean result = executor.invokeAny(tasks);
            log.info("Final result is: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();

    }
}
