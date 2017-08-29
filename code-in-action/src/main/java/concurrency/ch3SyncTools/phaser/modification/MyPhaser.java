package concurrency.ch3SyncTools.phaser.modification;

import lombok.extern.log4j.Log4j;

import java.util.concurrent.Phaser;

/**
 * Usage: <b> </b>
 *
 * @user lucifer
 * @date 2016-9-16
 * @device Aurora R5
 */
@Log4j
public class MyPhaser extends Phaser {
    @Override
    protected boolean onAdvance(int phase, int registeredParties) {
        switch (phase) {
            case 0:
                return studentsArrived();
            case 1:
                return finishFirstExercise();
            case 2:
                return finishSecondExercise();
            case 3:
                return finishExam();
            default:
                return true;
        }
    }

    private boolean studentsArrived() {
        log.info("Phaser: students are ready to take exam.");
        log.info(String.format("Phaser: we have %d students.",
                getRegisteredParties()));
        return false;
    }

    private boolean finishFirstExercise() {
        log.info("Phaser: all students finished first exercise.");
        log.info(String.format("Phaser: we have %d students.",
                getRegisteredParties()));
        return false;
    }

    private boolean finishSecondExercise() {
        log.info("Phaser: all students finished second exercise.");
        log.info(String.format("Phaser: we have %d students.",
                getRegisteredParties()));
        return false;
    }

    private boolean finishExam() {
        log.info("Phaser: all students finish exam.");
        log.info("Phaser: Thks");
        return true;
    }
}
