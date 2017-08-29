package concurrency.ch3SyncTools.phaser.modification;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> 控制并发阶段性任务的改变</b>
 *  一个学生参加测验的例子，所有学生做完第一道再开始下一道
 * 重写的Phaser类，覆盖了 onAdvance()， 可以在每次阶段改变时执行，来加入我们定义的操作
 * 返回 false 时继续执行，返回 true 时结束并进入 termination 状态
 *
 * @author lucifer
 *         Date 2016-9-16
 *         Device Aurora R5
 */
@Log4j
public class PhaserModMain {

    public static final int SIZE = 5;

    public static void main(String[] args) {
        MyPhaser phaser = new MyPhaser();
        Student[] students = new Student[SIZE];
        for (int i = 0; i < SIZE; i++) {
            students[i] = new Student(phaser);
            phaser.register();
        }

        Thread[] threads = new Thread[SIZE];
        for (int i = 0; i < SIZE; i++) {
            threads[i] = new Thread(students[i], "Student-" + i);
            threads[i].start();
        }

        for (int i = 0; i < SIZE; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        log.info(String.format("Main: The phaser has finished: %s.",
                phaser.isTerminated()));
    }
}
