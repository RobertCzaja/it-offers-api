package pl.api.itoffers.report;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

/**
 * todo test it somehow todo this class should be the only one class available from the package
 * outside
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportStatisticsCollector {

  private final ClockInterface clock;
  private Map<UUID, ScrapingStats> scrapingStats;

  public void start(UUID scrapingId, Origin.Provider provider) {
    if (scrapingStats.containsKey(scrapingId)) {
      throw new ImportStatisticsException(
          String.format("ScrappingId %s already created", scrapingId));
    }
    scrapingStats.put(scrapingId, new ScrapingStats(clock.now(), provider.name()));
  }

  public void newOfferAdded(UUID scrapingId, String technology) {
    scrapingIdExists(scrapingId);
    scrapingStats.get(scrapingId).newOfferAdded(technology);
  }

  public void end(UUID scrapingId) {
    scrapingIdExists(scrapingId);
    var stats = scrapingStats.get(scrapingId);
    var fullReport = stats.finish(clock.now());
    scrapingStats.remove(scrapingId);
    log.info(fullReport);
    // todo should send email
  }

  private void scrapingIdExists(UUID scrapingId) {
    if (!scrapingStats.containsKey(scrapingId)) {
      throw new ImportStatisticsException(
          String.format("ScrappingId %s does not exist", scrapingId));
    }
  }
}
