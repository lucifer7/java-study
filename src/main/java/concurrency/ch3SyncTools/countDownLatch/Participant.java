package concurrency.ch3SyncTools.countDownLatch;

import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> 会议参与者 </b>
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
public class Participant implements Runnable {
    private VideoConference conference;
    private String name;

    public Participant(VideoConference conference, String name) {
        this.conference = conference;
        this.name = name;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        conference.arrive(name);     // 表明当前参与者到达会议
    }
}
