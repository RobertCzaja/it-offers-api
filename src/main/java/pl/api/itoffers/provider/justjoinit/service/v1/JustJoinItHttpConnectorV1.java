package pl.api.itoffers.provider.justjoinit.service.v1;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.infrastructure.AbstractJustJoinItHttpConnector;
import pl.api.itoffers.provider.justjoinit.infrastructure.JustJoinItParameters;
import pl.api.itoffers.provider.justjoinit.service.v2.JustJoinItHttpConnectorV2;
import pl.api.itoffers.shared.http.connector.HttpConnector;

/**
 * @deprecated since 12.2024
 * @see JustJoinItHttpConnectorV2
 */
@Service
public final class JustJoinItHttpConnectorV1 extends AbstractJustJoinItHttpConnector {

  public JustJoinItHttpConnectorV1(JustJoinItParameters parameters, HttpConnector httpConnector) {
    super(parameters, httpConnector);
  }

  @Override
  protected String getRawJsonOffers(Document responseBody) {
    return responseBody.select("#__NEXT_DATA__").get(0).html();
  }
}
