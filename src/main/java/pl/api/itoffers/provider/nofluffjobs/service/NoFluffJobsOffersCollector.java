package pl.api.itoffers.provider.nofluffjobs.service;

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
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;
import pl.api.itoffers.provider.nofluffjobs.fetcher.RawDataMatcher;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawOffer;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsOffersCollector implements OffersCollector {
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsOfferRepository detailsOfferRepository;
  private final NoFluffJobsDetailsProvider detailsProvider;
  private final NoFluffJobsListProvider listProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;

  public void collectFromProvider(@NotNull final String customTechnology) {
    List<String> technologies = technologiesProvider.getTechnologies(customTechnology);
    UUID scrapingId = UUID.randomUUID();

    for (var technology : technologies) {
      var listOffers = fetchNoFluffJobsRawListOffers(technology, scrapingId);

      if (null == listOffers) {
        continue;
      }

      fetchDetailsOffers(listOffers);

      for (var draft : getDraftList(listOffers)) {
        offerSaver.save(draft);
      }
    }
  }

  /**
   * todo move to separated class & add common interface todo needs to get by "scrapingId" and
   * "technology"
   */
  private List<OfferDraft> getDraftList(List<NoFluffJobsRawListOffer> listOffers) {
    return RawDataMatcher.match(
            listOffers,
            detailsOfferRepository.findByOfferIdIn(
                listOffers.stream().map(NoFluffJobsRawListOffer::getOfferId).toList()))
        .stream()
        .map(
            offerToSave ->
                new OfferDraft(
                    OfferFactory.createOrigin(offerToSave.listOffer()),
                    OfferFactory.createOfferMetadata(
                        offerToSave.listOffer(), offerToSave.detailsOffer()),
                    OfferFactory.createCategories(offerToSave.detailsOffer()),
                    OfferFactory.createSalaries(offerToSave.listOffer()),
                    OfferFactory.createCompany(offerToSave.listOffer())))
        .toList();
  }

  private List<NoFluffJobsRawListOffer> fetchNoFluffJobsRawListOffers(
      String technology, UUID scrapingId) {
    try {
      listProvider.fetch(technology, scrapingId);
    } catch (Exception e) {
      log.error("Error on fetching list of {}: {}", technology, e.getMessage());
      return null;
    }
    return listOfferRepository.findByScrapingIdAndTechnology(scrapingId, technology);
  }

  private void fetchDetailsOffers(List<NoFluffJobsRawListOffer> listOffers) {
    listOffers.forEach(
        listOffer -> {
          String slug = (String) listOffer.getOffer().get("url");

          try {
            detailsProvider.fetch(slug, listOffer.getScrapingId(), listOffer.getOfferId());
          } catch (NoFluffJobsException e) {
            log.error("Error on fetching details offer: {}", e.getMessage());
          }
        });
  }

  private List<NoFluffJobsRawOffer> getOfferToSave(List<NoFluffJobsRawListOffer> listOffers) {
    return RawDataMatcher.match(
        listOffers,
        detailsOfferRepository.findByOfferIdIn(
            listOffers.stream().map(NoFluffJobsRawListOffer::getOfferId).toList()));
  }
}
