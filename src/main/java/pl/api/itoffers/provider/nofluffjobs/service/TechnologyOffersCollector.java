package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawOffer;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechnologyOffersCollector {
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsOfferRepository detailsOfferRepository;
  private final NoFluffJobsDetailsProvider detailsProvider;
  private final NoFluffJobsListProvider listProvider;
  private final OfferSaver offerSaver;

  /** todo write integration test for that full fetching and saving class */
  @Async
  public void fetchOffers(String technology) {
    UUID scrapingId = UUID.randomUUID();

    log.info("[{}] fetching offer list", technology);
    listProvider.fetch(technology, scrapingId);
    log.info("[{}] successfully fetched offer list", technology);

    var listOffers = listOfferRepository.findByScrapingIdAndTechnology(scrapingId, technology);

    listOffers.forEach(
        listOffer -> {
          String slug = (String) listOffer.getOffer().get("url");
          log.info("[{}] {}", technology, slug);

          try {
            detailsProvider.fetch(slug, listOffer.getScrapingId(), listOffer.getOfferId());
          } catch (NoFluffJobsException e) {
            log.warn(e.getMessage());
          }
        });

    log.info("[{}] saving domain offers", technology);

    var detailsOffers =
        detailsOfferRepository.findByOfferIdIn(
            listOffers.stream().map(NoFluffJobsRawListOffer::getOfferId).toList());
    var matchedOffers = match(listOffers, detailsOffers);

    for (var matchedOffer : matchedOffers) {
      offerSaver.save(
          OfferFactory.createOrigin(matchedOffer.listOffer()),
          OfferFactory.createOfferMetadata(matchedOffer.listOffer(), matchedOffer.detailsOffer()),
          OfferFactory.createCategories(matchedOffer.detailsOffer()),
          OfferFactory.createSalaries(matchedOffer.listOffer()),
          OfferFactory.createCompany(matchedOffer.listOffer()));
    }

    log.info("[{}] domain offers successfully saved", technology);
  }

  /** todo delegate to separated class todo add unit tests for class above */
  private static List<NoFluffJobsRawOffer> match(
      List<NoFluffJobsRawListOffer> listOffers, List<NoFluffJobsRawDetailsOffer> detailsOffers) {
    if (listOffers.size() != detailsOffers.size()) {
      throw NoFluffJobsException.becauseFetchingResultIsIncompatible("initial collections");
    }

    var matched = new ArrayList<NoFluffJobsRawOffer>();

    for (var listOffer : listOffers) {
      for (var detailsOffer : detailsOffers) {
        if (listOffer.getOfferId().equals(detailsOffer.getOfferId())) {
          matched.add(new NoFluffJobsRawOffer(listOffer, detailsOffer));
          break;
        }
      }
    }

    if (matched.size() != listOffers.size()) {
      throw NoFluffJobsException.becauseFetchingResultIsIncompatible("could not match each offer");
    }

    return matched;
  }
}
