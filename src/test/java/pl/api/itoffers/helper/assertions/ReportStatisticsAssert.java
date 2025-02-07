package pl.api.itoffers.helper.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import pl.api.itoffers.report.service.statisticsNotifier.InMemoryStatisticsNotifier;

@RequiredArgsConstructor
public class ReportStatisticsAssert {
  private final InMemoryStatisticsNotifier statisticsNotifier;

  public void expects(
      String expectedTitle,
      String expectedDay,
      String expectedFrom,
      String expectedTo,
      ExpectedTechnologyOffers... expectedTechnologyOffers) {
    // todo to implement
    assertThat("").isNotNull();
  }

  public record ExpectedTechnologyOffers(String technology, int fetchedOffers, int newOffers) {}
}
