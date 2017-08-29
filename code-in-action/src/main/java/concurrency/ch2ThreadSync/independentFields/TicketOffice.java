package concurrency.ch2ThreadSync.independentFields;

import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

/**
 * Created by lucifer on 2016-7-19.
 */
public class TicketOffice implements Runnable {
    private Cinema cinema;

    public TicketOffice(Cinema cinema) {
        this.cinema = cinema;
    }

    @Override
    public void run() {
        cinema.sellTicket1(RandomUtils.nextInt(0, (int) cinema.getVacanciesCinema1()));
        cinema.sellTicket1(RandomUtils.nextInt(0, (int) cinema.getVacanciesCinema1()));
        cinema.sellTicket1(RandomUtils.nextInt(0, (int) cinema.getVacanciesCinema1()));
        cinema.sellTicket2(RandomUtils.nextInt(0, (int) cinema.getVacanciesCinema2()));
        cinema.sellTicket2(RandomUtils.nextInt(0, (int) cinema.getVacanciesCinema2()));
        synchronized (cinema) {
            cinema.returnTicket1(RandomUtils.nextInt(0, 20- (int) cinema.getVacanciesCinema1()));
        }
    }
}
