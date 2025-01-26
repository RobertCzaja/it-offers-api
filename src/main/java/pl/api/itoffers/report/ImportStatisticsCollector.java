package pl.api.itoffers.report;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.Origin;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

/**
 * todo test it somehow todo this class should be the only one class available from the package
 * outside todo make sure in integration test that Spring produces only one instance (test it by
 * integration test)
 */
@Service
public class ImportStatisticsCollector {

  private final ClockInterface clock;
  private final Logger log;
  private final Map<UUID, ScrapingStats> scrapingStats = new HashMap<>();

  public ImportStatisticsCollector(ClockInterface clock, Logger log) {
    this.clock = clock;
    this.log = log;
  }

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
