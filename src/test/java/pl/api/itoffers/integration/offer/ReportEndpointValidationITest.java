package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.offer.helper.ReportAssert;
import pl.api.itoffers.integration.offer.helper.ReportEndpointCaller;

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

        ReportAssert.responseIs(response, HttpStatus.BAD_REQUEST, "dateFrom cannot be greater thant dateTo");
    }

    @Test
    public void shouldUserDifferentThanAdminIsNotAllowToGetCategoriesReport() {
        ResponseEntity<String> response = reportEndpointCaller.makeRequestAsUser();

        ReportAssert.responseIs(response, HttpStatus.FORBIDDEN, "Access denied");
    }

    @Test
    public void shouldNotAllowToPassMadeUpDateFrom() {
        ResponseEntity<String> response = reportEndpointCaller.makeRequest("2026-01-01", null);

        ReportAssert.responseIs(response, HttpStatus.BAD_REQUEST, "dateFrom cannot be greater thant dateTo");
    }
}
