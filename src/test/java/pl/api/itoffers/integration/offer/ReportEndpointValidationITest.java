package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.ui.controller.ReportController;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportEndpointValidationITest extends AbstractITest  {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldReturnErrorResponseOnInvalidDatesRange() {
        ResponseEntity<String> response = makeRequest("2024-09-01", "2024-08-01");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("dateFrom cannot be greater thant dateTo");
    }

    private ResponseEntity<String> makeRequest(String dateFrom, String dateTo) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ReportController.PATH_CATEGORY);

        if (null != dateFrom) {
            builder = builder.queryParam("dateFrom", dateFrom);
        }

        if (null != dateTo) {
            builder = builder.queryParam("dateTo", dateTo);
        }

        URI uri = builder.build().toUri();

        return template.exchange(
            uri,
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
            String.class
        );
    }

    // TODO test-scenario: only user with ADMIN role can pass
    // TODO test-scenario: user doesn't pass dateTo but dateFrom provided by User will be greater than today
}
