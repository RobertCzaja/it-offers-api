package pl.api.itoffers.integration.offer.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import pl.api.itoffers.helper.ApiAuthorizationHelper;
import pl.api.itoffers.helper.AuthorizationCredentials;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.ui.controller.ReportController;

import java.net.URI;
import java.util.List;

@Service
@Profile("test")
public class ReportCategoriesEndpointCaller {

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    public ResponseEntity<String> makeRequest(String dateFrom, String dateTo) {
        return template.exchange(
            createUri(dateFrom, dateTo, null),
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
            String.class
        );
    }

    public ResponseEntity<CategoriesStatisticsDto> makeRequestForObject(
        String dateFrom,
        String dateTo,
        List<String> technologies
    ) {
        return template.exchange(
                createUri(dateFrom, dateTo, technologies),
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

    private static URI createUri(String dateFrom, String dateTo, List<String> technologies) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ReportController.PATH_CATEGORY);

        if (null != dateFrom) {
            builder = builder.queryParam("dateFrom", dateFrom);
        }

        if (null != dateTo) {
            builder = builder.queryParam("dateTo", dateTo);
        }

        if (null != technologies) {
            builder = builder.queryParam("technologies", String.join(",", technologies));
        }

        return builder.build().toUri();
    }
}
