package pl.api.itoffers.unit.report.java;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import pl.api.itoffers.helper.FrozenClock;
import pl.api.itoffers.helper.assertions.ReportAssert;
import pl.api.itoffers.provider.Origin;
import pl.api.itoffers.report.java.ImportStatisticsCollector;
import pl.api.itoffers.report.java.ImportStatisticsException;

class ImportStatisticsCollectorTest {
  private static final UUID SCRAPING_ID = UUID.randomUUID();
  private static final UUID SCRAPING_ID_2 = UUID.randomUUID();

  private FrozenClock clock;
  private Logger logger;
  private ImportStatisticsCollector importStatisticsCollector;

  @BeforeEach
  public void setUp() {
    this.clock = new FrozenClock();
    this.logger = Mockito.mock(Logger.class);
    this.importStatisticsCollector = new ImportStatisticsCollector(this.clock, this.logger);
  }

  @Test
  void cannotStartCollectorTwice() {
    this.importStatisticsCollector.start(SCRAPING_ID, Origin.Provider.NO_FLUFF_JOBS);
    assertThrows(
        ImportStatisticsException.class,
        () -> this.importStatisticsCollector.start(SCRAPING_ID, Origin.Provider.NO_FLUFF_JOBS));
  }

  @Test
  void cannotEndCollectionWhenIsNotStarted() {
    assertThrows(
        ImportStatisticsException.class, () -> this.importStatisticsCollector.end(SCRAPING_ID));
  }

  @Test
  void shouldCorrectlyCollectsOffersAndReturnReportForMultipleScrapings() {
    this.importStatisticsCollector.start(SCRAPING_ID, Origin.Provider.NO_FLUFF_JOBS);
    this.importStatisticsCollector.newOfferAdded(SCRAPING_ID, "php");
    this.importStatisticsCollector.newOfferAdded(SCRAPING_ID, "java");
    this.importStatisticsCollector.newOfferAdded(SCRAPING_ID, "php");

    this.clock.setNow(LocalDateTime.of(2025, 1, 10, 17, 31, 28));
    this.importStatisticsCollector.end(SCRAPING_ID);

    verify(this.logger).info(ReportAssert.expectedReport());
  }
}
