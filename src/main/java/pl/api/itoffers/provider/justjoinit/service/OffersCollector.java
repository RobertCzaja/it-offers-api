package pl.api.itoffers.provider.justjoinit.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.factory.OfferFactory;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.nofluffjobs.service.TechnologyOffersCollector;

@Slf4j
@Service
@RequiredArgsConstructor
public class OffersCollector {
  private final TechnologyRepository technologyRepository;
  private final JustJoinItProvider justJoinItProvider;
  private final JustJoinItRepository jjitRawOffersRepository;
  private final SalariesFactory salariesFactory;
  private final OfferSaver offerSaver;

  /**
   * todo make it looks like (interface?)
   *
   * @see TechnologyOffersCollector
   */
  public void fetch(@NotNull String requestedTechnology) {
    List<String> technologies =
        requestedTechnology.isEmpty()
            ? technologyRepository.allActive()
            : List.of(requestedTechnology);

    UUID scrapingId = UUID.randomUUID();

    try {
      technologies.forEach(technology -> justJoinItProvider.fetch(technology, scrapingId));
    } catch (Exception e) {
      log.error("Error on fetching JustJoinIT offers", e);
      throw e;
    }

    for (JustJoinItRawOffer rawOffer : jjitRawOffersRepository.findByScrapingId(scrapingId)) {
      offerSaver.save(
          OfferFactory.createOrigin(rawOffer),
          OfferFactory.createOfferMetadata(rawOffer),
          OfferFactory.createCategories(rawOffer),
          salariesFactory.create(rawOffer),
          OfferFactory.createCompany(rawOffer));
    }
  }
}
