package concurrency.ch3SyncTools.phaser;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Phaser;

/**
 * Usage: <b> 运行阶段性并发任务，多个同构线程在同一个步骤节点同步，等全部线程到达后再进行下一步 </b>
 * 中途也可以 deregister
 * Phaser 初始化需要指定参与者个数，中途也能再加
 *
 * @author Jingyi.Yang
 *         Date 2016/9/14
 **/
@Log4j
public class PhaserMain {
    public static void main(String[] args) {
        // 1. Create a phaser with 4 participants
        Phaser phaser = new Phaser(4);

        FileSearch system = new FileSearch("C:\\Windows", "log", phaser);
        FileSearch app = new FileSearch("C:\\Program Files", "log", phaser);
        FileSearch user = new FileSearch("C:\\Users", "log", phaser);
        FileSearch document = new FileSearch("C:\\Documents And Settings", "log", phaser);

        Thread systemT = new Thread(system, "FileSearcher-system");
        Thread appT = new Thread(app, "FileSearcher-app");
        Thread userT = new Thread(user, "FileSearcher-user");
        Thread documentT = new Thread(document, "FileSearcher-document");
        systemT.start();
        appT.start();
        userT.start();
        documentT.start();

        try {
            systemT.join();
            appT.join();
            userT.join();
            documentT.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("All work complete.--");
    }
}
