package pl.api.itoffers.provider.nofluffjobs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.fetcher.details.NoFluffJobsDetailsProvider;
import pl.api.itoffers.provider.nofluffjobs.fetcher.list.NoFluffJobsListProvider;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoFluffJobsProvider {

  private final NoFluffJobsListProvider listProvider;
  private final NoFluffJobsListOfferRepository listOfferRepository;
  private final NoFluffJobsDetailsProvider detailsProvider;

  /**
   * TODO under construction
   */
  public void fetch() {

    String[] technologies = {"java", "php"};

    Arrays.stream(technologies).forEach(technology -> {
        UUID scrapingId = UUID.randomUUID();
        log.info("Fetch {}", technology);
        listProvider.fetch(technology, scrapingId);

        listOfferRepository
            .findByScrapingIdAndTechnology(scrapingId,technology)
            .forEach(listOffer -> {
                String slug = (String) listOffer.getOffer().get("url");
                log.info(slug);
                detailsProvider.fetch(slug, listOffer.getScrapingId(), listOffer.getOfferId());
            });

        log.info("Fetched successfully");
    });
  }
}
