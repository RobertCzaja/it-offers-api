package pl.api.itoffers.provider.nofluffjobs.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.factory.NoFluffJobsProviderImporterFactory;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class FetchNoFluffJobsOffersScheduledJob {
  private final NoFluffJobsProviderImporterFactory importerFactory;

  @Scheduled(cron = "0 0 */6 * * *")
  public void fetchNoFluffJobsOffers() {
    importerFactory.create().importOffers("");
  }
}
