package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.factory.JustJoinItProviderImporterFactory;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class FetchJustJoinItOffersScheduledJob {
  private final JustJoinItProviderImporterFactory importerFactory;

  @Scheduled(cron = "0 0 9 * * *")
  public void fetchJustJoinIt() {
    importerFactory.create().importOffers("");
  }
}
