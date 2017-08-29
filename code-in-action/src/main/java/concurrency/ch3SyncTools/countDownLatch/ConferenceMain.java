package concurrency.ch3SyncTools.countDownLatch;

/**
 * Usage: <b> 召集会议，测试类 </b>
 *
 * @user lucifer
 * @date 2016-9-10
 * @device Aurora R5
 */
public class ConferenceMain {
    public static void main(String[] args) {
        VideoConference conference = new VideoConference(10);
        Thread threadConference = new Thread(conference);
        threadConference.start();

        for (int i = 0; i < 10; i++) {
            Participant participant = new Participant(conference, "Participant-" + i);
            Thread p = new Thread(participant);
            p.start();
        }
    }
}
