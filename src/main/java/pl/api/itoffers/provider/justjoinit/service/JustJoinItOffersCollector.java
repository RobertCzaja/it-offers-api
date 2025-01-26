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
    List<String> technologies =
        technologiesProvider.getTechnologies(customTechnology); // todo to remove
    UUID scrapingId = UUID.randomUUID();

    fetchOffersFromExternalService(technologies, scrapingId); // todo to remove

    for (var technology : technologiesProvider.getTechnologies(customTechnology)) {
      // todo to implement
    }

    for (var draft : getDraftList(scrapingId)) {
      offerSaver.save(draft);
    }
  }

  /**
   * todo move to separated class & add common interface todo needs to get offers also by
   * "technology"
   */
  private List<OfferDraft> getDraftList(UUID scrapingId) {
    return jjitRawOffersRepository.findByScrapingId(scrapingId).stream()
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

  private void fetchOffersFromExternalService(List<String> technologies, UUID scrapingId) {
    try {
      technologies.forEach(technology -> justJoinItProvider.fetch(technology, scrapingId));
    } catch (Exception e) {
      log.error("Error on fetching JustJoinIT offers", e);
      throw e;
    }
  }
}
