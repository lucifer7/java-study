package concurrency.ch2ThreadSync.independentFields;

import lombok.extern.log4j.Log4j;

/**
 * Created by lucifer on 2016-7-19.
 * 电影院的例子，在同步的类里安排独立属性，可以分别被不同线程访问
 */
@Log4j
public class Cinema {
    private long vacanciesCinema1;
    private long vacanciesCinema2;

    private final Object controlCinema1, controlCinema2;

    public Cinema() {
        controlCinema1 = new Object();
        controlCinema2 = new Object();
        vacanciesCinema1 = 20;
        vacanciesCinema2 = 20;
    }

    public boolean sellTicket1(int number) {
        synchronized (controlCinema1) {
            if (number < vacanciesCinema1) {
                vacanciesCinema1 -= number;
                log.info("Minus: -" + number);
                log.info("Present vacancies: " + vacanciesCinema1);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean sellTicket2 (int number) {
        synchronized (controlCinema2) {
            if (number < vacanciesCinema2) {
                vacanciesCinema2 -= number;
                log.info("Minus: -" + number);
                log.info("Present vacancies: " + vacanciesCinema2);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean returnTicket1(int number) {
        synchronized (controlCinema1) {
            vacanciesCinema1 += number;
            log.info("Add: +" + number);
            log.info("Present vacancies: " + vacanciesCinema1);
            return true;
        }
    }

    public boolean returnTicket2(int number) {
        synchronized (controlCinema2) {
            vacanciesCinema2 += number;
            log.info("Add: +" + number);
            log.info("Present vacancies: " + vacanciesCinema2);
            return true;
        }
    }

    public long getVacanciesCinema1() {
        return vacanciesCinema1;
    }

    public long getVacanciesCinema2() {
        return vacanciesCinema2;
    }
}
