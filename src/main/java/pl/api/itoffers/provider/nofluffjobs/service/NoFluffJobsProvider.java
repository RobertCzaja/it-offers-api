package pl.api.itoffers.provider.nofluffjobs.service;

import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsProvider {

  private final NoFluffJobsListProvider listProvider;
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsProvider detailsProvider;

  /** TODO under construction */
  public void fetch() {

    String[] technologies = {"java", "php"};

    Arrays.stream(technologies)
        .forEach(
            technology -> {
              UUID scrapingId = UUID.randomUUID();
              log.info("[technology] fetching {}", technology);
              listProvider.fetch(technology, scrapingId);

              listOfferRepository
                  .findByScrapingIdAndTechnology(scrapingId, technology)
                  .forEach(
                      listOffer -> {
                        String slug = (String) listOffer.getOffer().get("url");
                        log.info("[{}] {}", technology, slug);

                        try {
                          detailsProvider.fetch(
                              slug, listOffer.getScrapingId(), listOffer.getOfferId());
                        } catch (NoFluffJobsException e) {
                          log.warn(e.getMessage());
                        }
                      });

              log.info("fetched successfully");
            });
  }
}
