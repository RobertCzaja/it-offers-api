package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.provider.ProviderImporter;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsProviderImporter implements ProviderImporter {
  private final NoFluffJobsProviderCollector collector;
  private final NoFluffJobsOfferDraftProvider offerDraftProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;

  public void importOffers(@NotNull final String customTechnology) {
    UUID scrapingId = UUID.randomUUID();

    for (var technology : technologiesProvider.getTechnologies(customTechnology)) {
      try {
        collector.collectOffers(scrapingId, technology);
      } catch (Exception e) {
        log.error("Error on fetching list of {}: {}", technology, e.getMessage());
        continue;
      }

      for (var draft : offerDraftProvider.getList(scrapingId, technology)) {
        offerSaver.save(draft);
      }
    }
  }
}
