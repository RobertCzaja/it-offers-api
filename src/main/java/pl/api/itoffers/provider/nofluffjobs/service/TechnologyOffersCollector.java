package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class TechnologyOffersCollector {
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsProvider detailsProvider;
  private final NoFluffJobsListProvider listProvider;

  @Async
  public void fetchOffers(String technology) {
    UUID scrapingId = UUID.randomUUID();

    log.info("[technology] fetching offer list: {}", technology);
    listProvider.fetch(technology, scrapingId);
    log.info("[technology] successfully fetched offer list: {}", technology);

    listOfferRepository
        .findByScrapingIdAndTechnology(scrapingId, technology)
        .forEach(
            listOffer -> {
              String slug = (String) listOffer.getOffer().get("url");
              log.info("[{}] {}", technology, slug);

              try {
                detailsProvider.fetch(slug, listOffer.getScrapingId(), listOffer.getOfferId());
              } catch (NoFluffJobsException e) {
                log.warn(e.getMessage());
              }
            });
  }
}
