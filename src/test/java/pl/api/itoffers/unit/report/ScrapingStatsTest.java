package pl.api.itoffers.unit.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.report.ScrapingStats;

public class ScrapingStatsTest {

  @Test
  void shouldCreateReport() {
    var scrapingStats =
        new ScrapingStats(
            LocalDateTime.of(2025, 1, 10, 17, 28, 5, 145000000),
            Origin.Provider.NO_FLUFF_JOBS.name());
    scrapingStats.newOfferAdded("php");
    scrapingStats.newOfferAdded("java");
    scrapingStats.newOfferAdded("php");

    var result = scrapingStats.finish(LocalDateTime.of(2025, 1, 10, 17, 31, 28, 562000000));

    assertThat(result)
        .isEqualTo(
            "Provider: NO_FLUFF_JOBS\n"
                + "Started at: 10 stycznia 2025, 17:28:05\n"
                + "Finished at: 10 stycznia 2025, 17:31:28\n"
                + "Time: 3m 23s\n"
                + "New offers for technologies:\n"
                + "java: 1 \n"
                + "php: 2 \n");
  }
}
