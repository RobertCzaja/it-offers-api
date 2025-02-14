package pl.api.itoffers.provider;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;

@Slf4j
@RequiredArgsConstructor
public class ProviderImporterTemplate implements ProviderImporter {
  private final ProviderCollector providerCollector;
  private final OfferDraftProvider offerDraftProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;
  private final ApplicationEventPublisher publisher;

  @Override
  public void importOffers(String customTechnology) {
    UUID scrapingId = UUID.randomUUID();
    var technologies = technologiesProvider.getTechnologies(customTechnology);
    publisher.publishEvent(
        new ImportStartedEvent(this, scrapingId, technologies, providerCollector.providerName()));

    for (var technology : technologies) {
      try {
        providerCollector.collectOffers(scrapingId, technology);
      } catch (Exception e) {
        log.error(
            "Error on fetching list of {}: {}", technology, e.getMessage()); /*todo #69 to remove*/
        publisher.publishEvent(new FetchListFailedEvent(this, scrapingId));
        continue;
      }

      for (var draft : offerDraftProvider.getList(scrapingId, technology)) {
        offerSaver.save(draft);
      }
    }
    publisher.publishEvent(new ImportFinishedEvent(this, scrapingId));
  }
}
