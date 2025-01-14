package pl.api.itoffers.offer.application.service;

import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.factory.OfferFactory;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferService {

  private final JustJoinItRepository jjitRawOffersRepository;
  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;
  private final OfferRepository offerRepository;
  private final OfferFactory offerFactory;
  private final SalariesFactory salariesFactory;

  public void processOffersFromExternalService(UUID scrappingId) {
    List<JustJoinItRawOffer> rawOffers = jjitRawOffersRepository.findByScrapingId(scrappingId);

    for (JustJoinItRawOffer rawOffer : rawOffers) {

      CategoryCollections categories = offerFactory.createCategories(rawOffer);
      Company company = offerFactory.createCompany(rawOffer);
      Offer offer =
          offerFactory.createOffer(
              rawOffer, categories.forEntity(), salariesFactory.create(rawOffer), company);

      Offer alreadyStoredOffer = findAlreadyStoredOffer(offer);

      if (null != alreadyStoredOffer) {
        continue;
      }

      companyRepository.save(company);
      categoryRepository.saveAll(categories.toSave());
      offerRepository.save(offer);
      log.info(
          "[jjit][{}] '{}' from {} at {}",
          offer.getTechnology(),
          offer.getTitle(),
          offer.getCompany().getName(),
          offer.getPublishedAt());
    }
  }

  private Offer findAlreadyStoredOffer(Offer offer) {
    return offerRepository.findByDifferentOffer(
        offer.getSlug(), offer.getTitle(), offer.getCompany().getName());
  }
}
