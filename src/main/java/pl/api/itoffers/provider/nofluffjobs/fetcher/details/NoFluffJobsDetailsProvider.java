package pl.api.itoffers.provider.nofluffjobs.fetcher.details;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@Service
@RequiredArgsConstructor
public class NoFluffJobsDetailsProvider {

  private final NoFluffJobsDetailsOfferRepository repository;
  private final NoFluffJobsDetailsFetcher fetcher;
  private final ClockInterface clock;

  public void fetch(String slug, UUID scrapingId, UUID listOfferId) {

    Map<String, Object> offerDetails = fetcher.fetch(slug);

    repository.save(
        new NoFluffJobsRawDetailsOffer(scrapingId, listOfferId, offerDetails, clock.now()));
  }
}
