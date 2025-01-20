package pl.api.itoffers.provider.nofluffjobs.ui.cli;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.service.NoFluffJobsProvider;

@Slf4j
@EnableScheduling
@Service
@RequiredArgsConstructor
public class FetchNoFluffJobsOffersScheduledJob {
  private final NoFluffJobsProvider noFluffJobsProvider;

  @Scheduled(cron = "0 0 */6 * * *")
  public void fetchNoFluffJobsOffers() {
    log.info("[nfj] start import");
    noFluffJobsProvider.fetch();
    log.info("[nfj] successfully imported");
  }
}
