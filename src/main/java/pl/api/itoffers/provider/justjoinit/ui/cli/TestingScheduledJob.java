package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@EnableScheduling
@Service
public class TestingScheduledJob {
    @Scheduled(cron="0 0/15 * * * *")
    public void testRunEvery15minutes() {
        log.info("[cron][start] testing cron run every 15 minutes");
    }

    @Scheduled(cron="0 0 9 * * *")
    public void testRunOncePerDay() {
        log.info("[cron][start] testing cron run once per day");
    }

    @Scheduled(cron="0 0 0/1 * * ?")
    public void testRunOnceEveryHour() {
        log.info("[cron][start] testing cron run every hour");
    }
}
