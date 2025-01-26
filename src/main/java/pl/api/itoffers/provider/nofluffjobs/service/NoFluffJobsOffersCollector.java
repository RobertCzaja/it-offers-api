package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.service.OfferSaver;
import pl.api.itoffers.offer.application.service.TechnologiesProvider;
import pl.api.itoffers.provider.OffersCollector;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsOffersCollector implements OffersCollector {
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsOfferDraftProvider offerDraftProvider;
  private final NoFluffJobsDetailsProvider detailsProvider;
  private final NoFluffJobsListProvider listProvider;
  private final OfferSaver offerSaver;
  private final TechnologiesProvider technologiesProvider;

  public void collectFromProvider(@NotNull final String customTechnology) {
    UUID scrapingId = UUID.randomUUID();

    for (var technology : technologiesProvider.getTechnologies(customTechnology)) {
      try {
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
      } catch (Exception e) {
        log.error("Error on fetching list of {}: {}", technology, e.getMessage());
        continue;
      }

      for (var draft : offerDraftProvider.getList(scrapingId, technology)) {
        offerSaver.save(draft);
      }
    }
  }
}
