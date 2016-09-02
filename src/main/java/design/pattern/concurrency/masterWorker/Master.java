package design.pattern.concurrency.masterWorker;

import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 * @date 2016-8-24
 * @device Yoga Pro
 */
@Log4j
public class Master {
    protected Queue<Object> workQueue = new ConcurrentLinkedDeque<>();      // 工作任务队列
    protected Map<String, Thread> threadMap = new HashMap<>();              // 工作者线程队列
    protected Map<String, Object> resultMap = new ConcurrentHashMap<>();    // 结果集合

    // 初始化构造器
    public Master(Worker worker, int count) {
        worker.setWorkQueue(workQueue);
        worker.setResultMap(resultMap);

        for (int i = 0; i < count; i++) {
            threadMap.put("Worker-" + i,
                    new Thread(worker, "Worker-" + i));
        }
    }

    // 查看状态，是否完成
    public boolean isComplete() {
        for (Map.Entry<String, Thread> entry : threadMap.entrySet()) {
            if (entry.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    // 提交一个工作任务
    public void submit(Object obj) {
        workQueue.add(obj);
    }

    // 开始所有工作进程
    public void start() {
        for (Map.Entry<String, Thread> worker : threadMap.entrySet()) {
            log.info("Start running thread: " + worker.getKey());
            worker.getValue().start();
        }
    }

    // 取得结果集并返回
    /*public Map<String, Object> getResultMap() {
        while (!isComplete()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return resultMap;
    }*/

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

}
