package pl.api.itoffers.integration.offer.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.ui.controller.ReportController;

import java.net.URI;

@Service
public class ReportEndpointCaller {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    public ResponseEntity<String> makeRequest(String dateFrom, String dateTo) {
        return template.exchange(
            createUri(dateFrom, dateTo),
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
            String.class
        );
    }

    public ResponseEntity<CategoriesStatisticsDto> makeRequestForObject(String dateFrom, String dateTo) {
        return template.exchange(
                createUri(dateFrom, dateTo),
                HttpMethod.GET,
                new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
                CategoriesStatisticsDto.class
        );
    }

    public ResponseEntity<String> makeRequestAsUser() {
        return template.exchange(
            ReportController.PATH_CATEGORY,
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.USER)),
            String.class
        );
    }

    private static URI createUri(String dateFrom, String dateTo) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ReportController.PATH_CATEGORY);

        if (null != dateFrom) {
            builder = builder.queryParam("dateFrom", dateFrom);
        }

        if (null != dateTo) {
            builder = builder.queryParam("dateTo", dateTo);
        }

        return builder.build().toUri();
    }
}
