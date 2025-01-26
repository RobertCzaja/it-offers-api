package pl.api.itoffers.provider.justjoinit.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.provider.OffersCollector;

@Slf4j
@Service
@RequiredArgsConstructor
public class JustJoinItOffersCollector implements OffersCollector {
  private final JustJoinItProvider justJoinItProvider;
  private final JustJoinItOfferDraftProvider offerDraftProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;

  public void collectFromProvider(@NotNull String customTechnology) {
    UUID scrapingId = UUID.randomUUID();

    for (var technology : technologiesProvider.getTechnologies(customTechnology)) {

      try {
        justJoinItProvider.fetch(technology, scrapingId);
      } catch (Exception e) {
        log.error("Error on fetching JustJoinIT offers", e);
        continue;
      }

      for (var draft : offerDraftProvider.getList(scrapingId, technology)) {
        offerSaver.save(draft);
      }
    }
  }
}
