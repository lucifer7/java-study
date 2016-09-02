package design.pattern.concurrency.masterWorker;

import java.util.Map;
import java.util.Queue;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 * @date 2016-8-23
 * @device Yoga Pro
 */
public abstract class Worker implements Runnable {
    // 任务队列，子任务
    protected Queue<Object> workQueue;
    // 结果集合
    protected Map<String, Object> resultMap;

    public void setWorkQueue(Queue<Object> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    /*public Object handle(Object input) {
        return input;
    }*/

    // 具体的任务处理在子类中实现
    public abstract Object handle(Object input);

    @Override
    public void run() {
        while (true) {
            Object input = workQueue.poll();
            if (input == null) break;
            Object result = handle(input);
            resultMap.put(Integer.toString(input.hashCode()), result);
        }
    }
}
