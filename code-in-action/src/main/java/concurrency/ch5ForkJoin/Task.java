package concurrency.ch5ForkJoin;

import common.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Usage: <b> Split tasks if size is too large, or execute directly </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/9/2
 **/
@Slf4j
public class Task extends RecursiveAction {
    private static final long serailVersionUID = 5565566556655656L;
    private List<User> users;
    private int first;
    private int last;
    private int suffix;

    public Task(List<User> users, int first, int last, int suffix) {
        this.users = users;
        this.first = first;
        this.last = last;
        this.suffix = suffix;
    }

    @Override
    protected void compute() {
        if (last - first < 10) {
            updateNicknames();
        } else {
            int middle = (last + first) / 2;
            log.info("Task: pending tasks {}", getQueuedTaskCount());
            Task t1 = new Task(users, first, middle + 1, suffix);
            Task t2 = new Task(users, middle + 1, last, suffix);
            invokeAll(t1, t2);      // synchronized call
        }
    }

    private void updateNicknames() {
        for (int i = first; i < last; i++) {
            User user = users.get(i);
            user.setNickName("NickName-" + i + "-" + suffix);
        }
    }
}
