package pl.api.itoffers.provider.nofluffjobs.fetcher.details;

import java.io.IOException;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.fetcher.NoFluffJobsParameters;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
@AllArgsConstructor
public class NoFluffJobsDetailsFetcher {

  private final HttpConnector httpConnector;
  private final NoFluffJobsParameters parameters;
  private final JsonFromOfferDetailsHtmlExtractor jsonPayloadExtractor;
  private final OfferDetailsFromJsonPayloadExtractor offerExtractor;

  public Map<String, Object> fetch(String slug) {
    try {
      String htmlPage = httpConnector.fetchSourceHtml(parameters.detailsUrl(slug));

      return offerExtractor.extractOffer(
          jsonPayloadExtractor.getRawJsonFromHtml(Jsoup.parse(htmlPage)));
    } catch (IOException e) {
      throw NoFluffJobsException.onFetchingHtmlPage(e);
    }
  }
}
