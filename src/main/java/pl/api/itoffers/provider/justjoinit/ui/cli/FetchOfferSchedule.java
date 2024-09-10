package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.FetchJustJoinItOffersService;

@Slf4j
@EnableScheduling
@Service
public class FetchOfferSchedule {
    @Autowired
    private FetchJustJoinItOffersService fetchJustJoinItOffersService;

    @Scheduled(cron="0 9 * * *")
    public void fetchJustJoinIt() {
        log.info("[just-join-it][cron] Start fetching scheduled job");
        fetchJustJoinItOffersService.fetch(null);
    }
}
