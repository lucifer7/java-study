package concurrency.ch5ForkJoin;

import common.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> By default, fork-join pool would create threads according to the num of processors </b>
 * Algorithm work-stealing only apply for ForkJoinTask
 *
 * @author Jingyi.Yang
 *         Date 2017/9/2
 **/
@Slf4j
public class Main {
    public static void main(String[] args) {
        UserListGenerator generator = new UserListGenerator();
        List<User> users = generator.generate(1000);

        Task task = new Task(users, 0, users.size(), 100);
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(task);     // async call

        do {
            log.info("Main: thread count: {}", pool.getActiveThreadCount());
            log.info("Main: thread steal: {}", pool.getStealCount());
            log.info("Main: parallelism: {}", pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        log.info("Main: execution finished");
    }
}
