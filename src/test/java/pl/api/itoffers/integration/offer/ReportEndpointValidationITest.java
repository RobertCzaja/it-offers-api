package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.offer.helper.ReportEndpointCaller;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportEndpointValidationITest extends AbstractITest  {
    @Autowired
    private ReportEndpointCaller reportEndpointCaller;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturnErrorResponseOnInvalidDatesRange() {
        ResponseEntity<String> response = reportEndpointCaller.makeRequest("2024-09-01", "2024-08-01");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("dateFrom cannot be greater thant dateTo");
    }

    @Test
    public void shouldUserDifferentThanAdminIsNotAllowToGetCategoriesReport() {
        ResponseEntity<String> response = reportEndpointCaller.makeRequestAsUser();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).contains("Access denied");
    }

    @Test
    public void shouldNotAllowToPassMadeUpDateFrom() {
        ResponseEntity<String> response = reportEndpointCaller.makeRequest("2026-01-01", null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("dateFrom cannot be greater thant dateTo");
    }
}
