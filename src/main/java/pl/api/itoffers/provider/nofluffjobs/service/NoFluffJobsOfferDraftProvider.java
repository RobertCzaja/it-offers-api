package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.OfferDraft;
import pl.api.itoffers.provider.OfferDraftProvider;
import pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory;
import pl.api.itoffers.provider.nofluffjobs.fetcher.RawDataMatcher;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Service
@RequiredArgsConstructor
public class NoFluffJobsOfferDraftProvider implements OfferDraftProvider {

  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsOfferRepository detailsOfferRepository;

  @Override
  public List<OfferDraft> getList(UUID scrapingId, String technology) {
    var listOffers = listOfferRepository.findByScrapingIdAndTechnology(scrapingId, technology);
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
}
