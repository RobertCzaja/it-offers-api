package pl.api.itoffers.offer.application.service;

import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.factory.OfferFactory;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;

/**
 * @deprecated todo to remove
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {

  private final JustJoinItRepository jjitRawOffersRepository;
  private final SalariesFactory salariesFactory;
  private final OfferSaver offerSaver;

  public void processOffersFromExternalService(UUID scrappingId) {
    List<JustJoinItRawOffer> rawOffers = jjitRawOffersRepository.findByScrapingId(scrappingId);

    for (JustJoinItRawOffer rawOffer : rawOffers) {
      try {
        offerSaver.save(
            OfferFactory.createOrigin(rawOffer),
            OfferFactory.createOfferMetadata(rawOffer),
            OfferFactory.createCategories(rawOffer),
            salariesFactory.create(rawOffer),
            OfferFactory.createCompany(rawOffer));
      } catch (Exception e) {
        log.error(
            "Error on saving JJIT offer ({}) as domain model: {}",
            rawOffer.getId(),
            rawOffer.getOffer());
        throw e;
      }
    }
  }
}
