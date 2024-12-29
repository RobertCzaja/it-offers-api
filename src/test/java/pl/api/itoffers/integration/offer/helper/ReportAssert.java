package pl.api.itoffers.integration.offer.helper;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ReportAssert {

  public static void responseIs(
      ResponseEntity<String> response, HttpStatus expectedHttpStatus, String expectedMessage) {
    assertThat(response.getStatusCode()).isEqualTo(expectedHttpStatus);
    assertThat(response.getBody()).contains(expectedMessage);
  }
}
