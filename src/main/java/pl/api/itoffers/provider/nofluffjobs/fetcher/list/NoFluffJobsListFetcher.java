package pl.api.itoffers.provider.nofluffjobs.fetcher.list;

import java.util.ArrayList;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NoFluffJobsListFetcher {

  private final NoFluffJobsConnector connector;
  private final OffersListFromJsonPayloadExtractor extractor;

  public ArrayList<Map<String, Object>> fetch(String technology) {
    return extractor.extractOffers(connector.fetchStringifyJsonPayload(technology));
  }
}
