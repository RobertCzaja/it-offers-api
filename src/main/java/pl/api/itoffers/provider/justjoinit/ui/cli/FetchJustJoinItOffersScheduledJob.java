package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItProviderImporter;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class FetchJustJoinItOffersScheduledJob {
  private final JustJoinItProviderImporter importer;

  @Scheduled(cron = "0 0 9 * * *")
  public void fetchJustJoinIt() {
    importer.importOffers("");
  }
}
