package pl.api.itoffers.helper;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import pl.api.itoffers.offer.domain.OfferDraft;
import pl.api.itoffers.report.service.ImportStatistics;

@AllArgsConstructor
public class ImportStatisticsInitializer {
  private final ImportStatistics importStatistics;

  public void initialize(OfferDraft draft) {
    importStatistics.start(
        draft.origin().getScrappingId(),
        List.of(draft.metadata().technology()),
        draft.origin().getProvider().name());
  }

  public void finish(UUID scrapingId) {
    importStatistics.finish(scrapingId);
  }
}
