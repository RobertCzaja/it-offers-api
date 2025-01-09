package pl.api.itoffers.provider.nofluffjobs.fetcher.list;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@Service
@RequiredArgsConstructor
public class NoFluffJobsListProvider {
  private final NoFluffJobsListOfferRepository repository;
  private final NoFluffJobsListFetcher fetcher;
  private final ClockInterface clock;

  public void fetch(String technology, UUID scrapingId) {
    ArrayList<Map<String, Object>> offers = fetcher.fetch(technology);
    offers.forEach(
        offer ->
            repository.save(
                new NoFluffJobsRawListOffer(
                    scrapingId, UUID.randomUUID(), technology, offer, clock.now())));
  }
}
