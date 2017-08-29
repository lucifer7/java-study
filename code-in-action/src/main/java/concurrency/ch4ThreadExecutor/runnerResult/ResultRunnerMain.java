package concurrency.ch4ThreadExecutor.runnerResult;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Usage: <b> Callable 任务提交给Executor框架，得到返回的Future类，并根据Future判断线程执行的情况和结果 </b>
 *
 * @author lucifer
 *         Date 2016-10-13
 *         Device Aurora R5
 */
@Log4j
public class ResultRunnerMain {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        List<Future<Integer>> resultList = Lists.newArrayList();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Integer number = random.nextInt(10);
            log.info("Main: number: " + number);
            FactorialCalculator calculator = new FactorialCalculator(number);
            Future<Integer> future = executor.submit(calculator);
            resultList.add(future);
        }

        do {
            log.info("Main: Number of completed tasks: " + executor.getCompletedTaskCount());
            for (int i = 0; i < resultList.size(); i++) {
                Future<Integer> result = resultList.get(i);
                log.info(result.isDone());
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (executor.getCompletedTaskCount() < resultList.size());
        
        log.info("Main: ---- Results ----");
        for (int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
            try {
                Integer number = result.get();
                log.info(String.format("Main: Task-%d: %d.", i, number));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
