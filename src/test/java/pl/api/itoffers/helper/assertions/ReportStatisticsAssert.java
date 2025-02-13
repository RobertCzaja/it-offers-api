package pl.api.itoffers.helper.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.report.service.statisticsNotifier.InMemoryStatisticsNotifier;

@Service
@RequiredArgsConstructor
public class ReportStatisticsAssert {
  private final InMemoryStatisticsNotifier statisticsNotifier;

  public void expects(String expectedTitle, ExpectedTechnologyOffers... expectedTechnologyOffers) {
    var details = statisticsNotifier.getReportDetails();
    assertThat(details.get("title")).isEqualTo(expectedTitle);

    for (var expectedTechnologyOffer : expectedTechnologyOffers) {
      var technologies = (Map<String, Map<String, Integer>>) details.get("technologies");
      boolean found = false;

      for (Map.Entry<String, Map<String, Integer>> technologyEntry : technologies.entrySet()) {
        if (expectedTechnologyOffer.technology.equals(technologyEntry.getKey())) {
          assertThat(technologyEntry.getValue().get("fetched"))
              .isEqualTo(expectedTechnologyOffer.fetchedOffers);
          assertThat(technologyEntry.getValue().get("new"))
              .isEqualTo(expectedTechnologyOffer.newOffers);
          found = true;
          break;
        }
      }

      if (!found) {
        throw new RuntimeException(
            String.format("Technology %s not found", expectedTechnologyOffer.technology));
      }
    }
  }

  public record ExpectedTechnologyOffers(String technology, int fetchedOffers, int newOffers) {}
}
