package concurrency.ch2ThreadSync.independentFields;

import lombok.extern.log4j.Log4j;

/**
 * Created by lucifer on 2016-7-19.
 */
@Log4j
public class CinemaMain {
    public static void main(String[] args) {
        Cinema cinema = new Cinema();

        TicketOffice office1 = new TicketOffice(cinema);
        TicketOffice office2 = new TicketOffice(cinema);

        Thread thread1 = new Thread(office1, "TicketOffice1");
        Thread thread2 = new Thread(office2, "TicketOffice2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("Present vacancies FOR cinema1: " + cinema.getVacanciesCinema1());
        log.info("Present vacancies FOR cinema2: " + cinema.getVacanciesCinema2());
    }
}
