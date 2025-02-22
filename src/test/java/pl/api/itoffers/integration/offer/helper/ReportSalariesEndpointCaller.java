package pl.api.itoffers.integration.offer.helper;

import java.net.URI;
import java.util.List;
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
import pl.api.itoffers.offer.application.dto.outgoing.offersalaries.OffersSalariesDto;
import pl.api.itoffers.offer.ui.controller.ReportController;

/**
 * @deprecated todo should be removed in #59
 */
@Service
@Profile("test")
public class ReportSalariesEndpointCaller {

  private static final String PATH = ReportController.PATH_SALARIES;

  @Autowired private TestRestTemplate template;
  @Autowired private ApiAuthorizationHelper apiAuthorizationHelper;

  public ResponseEntity<OffersSalariesDto> makeRequest(
      Integer amountTo, List<String> technologies, String dateFrom, String dateTo) {
    return template.exchange(
        createUri(amountTo, technologies, dateFrom, dateTo),
        HttpMethod.GET,
        new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.ADMIN)),
        OffersSalariesDto.class);
  }

  public ResponseEntity<String> makeRequestAsUser() {
    return template.exchange(
        PATH,
        HttpMethod.GET,
        new HttpEntity<>(apiAuthorizationHelper.getHeaders(AuthorizationCredentials.USER)),
        String.class);
  }

  private static URI createUri(
      Integer amountTo, List<String> technologies, String dateFrom, String dateTo) {
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
