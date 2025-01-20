package pl.api.itoffers.unit.report;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pl.api.itoffers.report.ImportSummary;

/**
 * @deprecated
 */
public class ImportSummaryTest {

  @Test
  void shouldCorrectlyBuildAReport() {
    var summary = new ImportSummary();

    summary.newOfferAdded("java");
    summary.newOfferAdded("php");
    summary.newOfferAdded("java");

    assertThat(summary.getReport()).isEqualTo("java: 2 \n" + "php: 1 \n");
  }
}
