package concurrency.ch3SyncTools.phaser.modification;

import lombok.extern.log4j.Log4j;

import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * Usage: <b> </b>
 *
 * @user lucifer
 * @date 2016-9-16
 * @device Aurora R5
 */
@Log4j
public class Student implements Runnable {
    private Phaser phaser;

    public Student(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        log.info(String.format("%s: arrived for the exam. %s",
                Thread.currentThread().getName(),
                new Date()));
        phaser.arriveAndAwaitAdvance();

        log.info(String.format("%s: is going to do first exercise. %s",
                Thread.currentThread().getName(),
                new Date()));
        doExercise1();
        log.info(String.format("%s: has finished first exercise.. %s",
                Thread.currentThread().getName(),
                new Date()));
        phaser.arriveAndAwaitAdvance();

        log.info(String.format("%s: is going to do second exercise. %s",
                Thread.currentThread().getName(),
                new Date()));
        doExercise2();
        log.info(String.format("%s: has finished second exercise. %s",
                Thread.currentThread().getName(),
                new Date()));
        phaser.arriveAndAwaitAdvance();

        log.info(String.format("%s: is going to do third exercise. %s",
                Thread.currentThread().getName(),
                new Date()));
        doExercise3();
        log.info(String.format("%s: has finished the exam. %s",
                Thread.currentThread().getName(),
                new Date()));
        phaser.arriveAndAwaitAdvance();
    }

    private void doExercise1() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doExercise2() {
        doExercise1();
    }

    private void doExercise3() {
        doExercise1();
    }
}
