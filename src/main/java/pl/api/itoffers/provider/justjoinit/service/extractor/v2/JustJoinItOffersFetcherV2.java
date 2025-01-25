package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;

/** Valid since 12.2024 */
@Service
public class JustJoinItOffersFetcherV2 implements JustJoinItOffersFetcher {

  @Autowired
  @Qualifier("connectionV2")
  private final JustJoinItConnector connector;

  @Autowired private final PayloadFromJsonExtractor extractor;

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
