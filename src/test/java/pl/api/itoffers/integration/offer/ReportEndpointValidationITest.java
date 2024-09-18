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

        URI uri = UriComponentsBuilder
                .fromUriString(ReportController.PATH_CATEGORY)
                .queryParam("dateFrom", "2024-09-01")
                .queryParam("dateTo", "2024-08-01")
                .build()
                .toUri();

        ResponseEntity<String> response = template.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
                String.class//CategoriesStatisticsDto.class
        );

        // TODO: type hint must be set from String to CategoriesStatisticsDto.class
        // TODO move template.exchange method to some private method to reduce code lines
        // TODO on error it cannot returns HTTP 403 - 400 instead

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
