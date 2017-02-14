package concurrency.ch4ThreadExecutor.multiRunnerAllResult;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/2/14
 **/
@Slf4j
public class RunnerAllResultsMain {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            taskList.add(new Task("Task " + i));
        }

        List<Future<Result>> futures = null;
        try {
            futures = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        log.info("Main: printing the results");
        for (int i = 0; i < futures.size(); i++) {
            Future<Result> resultFuture = futures.get(i);
            try {
                Result result = resultFuture.get();
                log.info("{}: {}", result.getName(), result.getValue());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
