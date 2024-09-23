package pl.api.itoffers.integration.offer.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportAssert {

    public static void responseIs(ResponseEntity<String> response, HttpStatus expectedHttpStatus, String expectedMessage) {
        assertThat(response.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(response.getBody()).contains(expectedMessage);
    }
}