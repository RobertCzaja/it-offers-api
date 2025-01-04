package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.infrastructure.AbstractJustJoinItHttpConnector;
import pl.api.itoffers.provider.justjoinit.infrastructure.JustJoinItParameters;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
public final class JustJoinItHttpConnectorV2 extends AbstractJustJoinItHttpConnector {

  public JustJoinItHttpConnectorV2(JustJoinItParameters parameters, HttpConnector httpConnector) {
    super(parameters, httpConnector);
  }

  @Override
  protected String getRawJsonOffers(Document responseBody) {
    return new JsonFromHtmlExtractor().getRawJsonFromHtml(responseBody);
  }
}
