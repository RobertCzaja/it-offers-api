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
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.offer.ui.controller.ReportController;

import java.net.URI;

@Service
@Profile("test")
public class ReportSalariesEndpointCaller {

    private static final String PATH = ReportController.PATH_SALARIES;

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    public ResponseEntity<OffersDto> makeRequest(Integer amountTo) {
        return template.exchange(
            createUri(amountTo),
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
            OffersDto.class
        );
    }

    public ResponseEntity<String> makeRequestAsUser() {
        return template.exchange(
            PATH,
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.USER)),
            String.class
        );
    }

    private static URI createUri(Integer amountTo) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PATH);

        if (null != amountTo) {
            builder = builder.queryParam("to", amountTo);
        }

        return builder.build().toUri();
    }
}
