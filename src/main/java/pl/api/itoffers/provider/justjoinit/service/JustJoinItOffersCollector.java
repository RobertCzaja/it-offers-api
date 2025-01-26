package pl.api.itoffers.provider.justjoinit.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.offer.domain.OfferDraft;
import pl.api.itoffers.provider.OffersCollector;
import pl.api.itoffers.provider.justjoinit.factory.OfferFactory;
import pl.api.itoffers.provider.justjoinit.factory.SalariesFactory;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class JustJoinItOffersCollector implements OffersCollector {
  private final JustJoinItProvider justJoinItProvider;
  private final JustJoinItRepository jjitRawOffersRepository;
  private final SalariesFactory salariesFactory;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;

  public void collectFromProvider(@NotNull String customTechnology) {
    UUID scrapingId = UUID.randomUUID();

    for (var technology : technologiesProvider.getTechnologies(customTechnology)) {

      try {
        justJoinItProvider.fetch(technology, scrapingId);
      } catch (Exception e) {
        log.error("Error on fetching JustJoinIT offers", e);
        continue; // todo add integration test
      }

      for (var draft : getDraftList(scrapingId, technology)) {
        offerSaver.save(draft);
      }
    }
  }

  /** todo move to separated class & add common interface */
  private List<OfferDraft> getDraftList(UUID scrapingId, String technology) {
    return jjitRawOffersRepository.findByScrapingIdAndTechnology(scrapingId, technology).stream()
        .map(
            rawOffer ->
                new OfferDraft(
                    OfferFactory.createOrigin(rawOffer),
                    OfferFactory.createOfferMetadata(rawOffer),
                    OfferFactory.createCategories(rawOffer),
                    salariesFactory.create(rawOffer),
                    OfferFactory.createCompany(rawOffer)))
        .toList();
  }
}
