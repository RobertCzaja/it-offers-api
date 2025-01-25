package pl.api.itoffers.provider.justjoinit.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.factory.OfferFactory;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.provider.general.OffersCollector;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
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

  public void collectFromProvider(@NotNull String technology) {
    List<String> technologies = technologiesProvider.getTechnologies(technology);
    UUID scrapingId = UUID.randomUUID();

    fetchOffersFromExternalService(technologies, scrapingId);

    for (JustJoinItRawOffer rawOffer : jjitRawOffersRepository.findByScrapingId(scrapingId)) {
      offerSaver.save(
          OfferFactory.createOrigin(rawOffer),
          OfferFactory.createOfferMetadata(rawOffer),
          OfferFactory.createCategories(rawOffer),
          salariesFactory.create(rawOffer),
          OfferFactory.createCompany(rawOffer));
    }
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
