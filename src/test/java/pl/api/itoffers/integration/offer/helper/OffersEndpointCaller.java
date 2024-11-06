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
import pl.api.itoffers.offer.ui.controller.OfferController;

import java.net.URI;
import java.util.List;

@Service
@Profile("test")
public class OffersEndpointCaller {
    private static final String PATH = OfferController.PATH_OFFERS;

    @Autowired
    private TestRestTemplate template;
    @Autowired
    private ApiAuthorizationHelper apiAuthorizationHelper;

    public ResponseEntity<OffersDto> makeRequest(
        Integer amountTo,
        List<String> technologies,
        String dateFrom,
        String dateTo
    ) {
        return template.exchange(
            createUri(amountTo, technologies, dateFrom, dateTo),
            HttpMethod.GET,
            new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
            OffersDto.class
        );
    }

    private static URI createUri(
        Integer amountTo,
        List<String> technologies,
        String dateFrom,
        String dateTo
    ) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(PATH);

        if (null != amountTo) {
            builder = builder.queryParam("to", amountTo);
        }

        if (null != technologies) {
            builder = builder.queryParam("technologies", String.join(",", technologies));
        }

        if (null != dateFrom) {
            builder = builder.queryParam("dateFrom", dateFrom);
        }

        if (null != dateTo) {
            builder = builder.queryParam("dateTo", dateTo);
        }

        return builder.build().toUri();
    }
}