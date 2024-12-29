package pl.api.itoffers.provider.justjoinit.service.extractor.v1;

import java.util.ArrayList;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;
import pl.api.itoffers.provider.justjoinit.service.extractor.v2.JustJoinItOffersFetcherV2;

/**
 * Valid between 08.2024 and 12.2024 then JJIT changed returned HTML structure, and it required V2
 * implementation
 *
 * @deprecated the one below is the current one:
 * @see JustJoinItOffersFetcherV2
 */
@Service
@RequiredArgsConstructor
public class JustJoinItOffersFetcherV1 implements JustJoinItOffersFetcher {
  private final JustJoinItConnector connector;
  private final JustJoinItPayloadExtractor payloadExtractor;

  @Override
  public ArrayList<Map<String, Object>> fetch(String technology) {
    return payloadExtractor.extract(connector.fetchStringifyJsonPayload(technology));
  }
}
