package pl.api.itoffers.provider.nofluffjobs.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.service.NoFluffJobsProviderImporter;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class FetchNoFluffJobsOffersScheduledJob {
  private final NoFluffJobsProviderImporter importer;

  @Scheduled(cron = "0 0 */6 * * *")
  public void fetchNoFluffJobsOffers() {
    importer.importOffers("");
  }
}
