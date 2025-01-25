package pl.api.itoffers.provider.justjoinit.service.v2;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;

/** Valid since 12.2024 */
@Service
public class JustJoinItOffersFetcherV2 implements JustJoinItOffersFetcher {

  private final JustJoinItConnector connector;
  private final PayloadFromJsonExtractor extractor;

  public JustJoinItOffersFetcherV2(
      JustJoinItConnector connector, PayloadFromJsonExtractor extractor) {
    this.connector = connector;
    this.extractor = extractor;
  }

  @Override
  public ArrayList<Map<String, Object>> fetch(String technology) {
    return extractor.extractPayload(connector.fetchStringifyJsonPayload(technology));
  }
}
