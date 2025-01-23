package pl.api.itoffers.unit.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.assertions.ReportAssert;
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

    assertThat(result).isEqualTo(ReportAssert.expectedReport());
  }
}
