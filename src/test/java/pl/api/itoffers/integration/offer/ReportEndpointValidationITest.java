package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.offer.helper.ReportAssert;
import pl.api.itoffers.integration.offer.helper.ReportCategoriesEndpointCaller;

public class ReportEndpointValidationITest extends AbstractITest {
  @Autowired private ReportCategoriesEndpointCaller reportCategoriesEndpointCaller;

  @ParameterizedTest
  @CsvSource(
      value = {"2024-09-01:2024-08-01", "2026-01-01:"},
      delimiter = ':')
  public void shouldReturnErrorResponseOnInvalidDatesRange(String dateFrom, String dateTo) {
    ResponseEntity<String> response = reportCategoriesEndpointCaller.makeRequest(dateFrom, dateTo);

    ReportAssert.responseIs(
        response, HttpStatus.BAD_REQUEST, "dateFrom cannot be greater thant dateTo");
  }

  @Test
  public void shouldUserDifferentThanAdminIsNotAllowToGetCategoriesReport() {
    ResponseEntity<String> response = reportCategoriesEndpointCaller.makeRequestAsUser();

    ReportAssert.responseIs(response, HttpStatus.FORBIDDEN, "Access denied");
  }
}
