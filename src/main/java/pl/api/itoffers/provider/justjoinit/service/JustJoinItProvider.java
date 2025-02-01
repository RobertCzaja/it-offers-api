package pl.api.itoffers.provider.justjoinit.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.report.service.ImportStatistics;

@Service
@RequiredArgsConstructor
public class JustJoinItProvider {

  private final JustJoinItOffersFetcher justJoinItOffersFetcher;
  private final JustJoinItRepository repository;
  private final ImportStatistics importStatistics;

  public void fetch(String technology, UUID scrapingId) {
    ArrayList<Map<String, Object>> offers = justJoinItOffersFetcher.fetch(technology);
    offers.forEach(
        offer -> {
          repository.save(
              new JustJoinItRawOffer(scrapingId, technology, offer, LocalDateTime.now()));
          importStatistics.registerFetchedOffer(scrapingId, technology);
        });
  }
}
