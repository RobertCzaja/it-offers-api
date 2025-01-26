package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.ProviderCollector;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsProviderCollector implements ProviderCollector {

  private final NoFluffJobsListProvider listProvider;
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsProvider detailsProvider;

  @Override
  public void collectOffers(UUID scrapingId, String technology) {
    listProvider.fetch(technology, scrapingId);
    listOfferRepository
        .findByScrapingIdAndTechnology(scrapingId, technology)
        .forEach(
            listOffer -> {
              String slug = (String) listOffer.getOffer().get("url");

              try {
                detailsProvider.fetch(slug, listOffer.getScrapingId(), listOffer.getOfferId());
              } catch (NoFluffJobsException e) {
                log.error("Error on fetching details offer: {}", e.getMessage());
              }
            });
  }
}
