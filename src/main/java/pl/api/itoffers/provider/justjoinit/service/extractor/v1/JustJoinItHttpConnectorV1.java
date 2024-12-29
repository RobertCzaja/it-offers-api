package pl.api.itoffers.provider.justjoinit.service.extractor.v1;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.infrastructure.AbstractJustJoinItHttpConnector;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.JustJoinItHttpConnectorV2;

/**
 * @deprecated since 12.2024
 * @see JustJoinItHttpConnectorV2
 */
@Service
public final class JustJoinItHttpConnectorV1 extends AbstractJustJoinItHttpConnector {
  @Override
  protected String getRawJsonOffers(Document responseBody) {
    return responseBody.select("#__NEXT_DATA__").get(0).html();
  }
}
