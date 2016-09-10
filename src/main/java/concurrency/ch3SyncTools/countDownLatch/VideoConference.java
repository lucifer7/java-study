package concurrency.ch3SyncTools.countDownLatch;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.CountDownLatch;

/**
 * Usage: <b> 视频会议，等待N个与会者到齐，即可开始会议 </b>
 * 使用 CountDownLatch 进行等待
 * 1. 初始值 number：需要等待的事件的数量
 * 2. await()：等待全部事件完成的线程调用此方法等待
 * 3. countDown()：某个事件结束后调用此方法，表明完结
 *
 * 只能使用一次，单向减小到0
 *
 * @user lucifer
 * @date 2016-9-3
 * @device Aurora R5
 */
@Log4j
public class VideoConference implements Runnable {
    private final CountDownLatch controller;

    public VideoConference(int number) {
        this.controller = new CountDownLatch(number);
    }

    /* New arrival */
    public void arrive(String name) {
        log.info(String.format("%s has arrived.", name));
        controller.countDown();
        log.info(String.format("Video conference: waiting for %d participants.", controller.getCount()));
    }

    @Override
    public void run() {
        log.info(String.format("Video conference: Initialization, %d participants.", controller.getCount()));
        try {
            controller.await();
            log.info("Video conference: All participants have arrived.");
            log.info("Video conference: Let's start meeting...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
