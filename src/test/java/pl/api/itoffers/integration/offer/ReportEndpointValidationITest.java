package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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

    @ParameterizedTest
    @CsvSource(value = {
        "2024-09-01:2024-08-01",
        "2026-01-01:"
    }, delimiter = ':')
    public void shouldReturnErrorResponseOnInvalidDatesRange(String dateFrom, String dateTo) {
        ResponseEntity<String> response = reportEndpointCaller.makeRequest(dateFrom, dateTo);

        ReportAssert.responseIs(response, HttpStatus.BAD_REQUEST, "dateFrom cannot be greater thant dateTo");
    }

    @Test
    public void shouldUserDifferentThanAdminIsNotAllowToGetCategoriesReport() {
        ResponseEntity<String> response = reportEndpointCaller.makeRequestAsUser();

        ReportAssert.responseIs(response, HttpStatus.FORBIDDEN, "Access denied");
    }
}
