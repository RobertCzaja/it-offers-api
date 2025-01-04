package pl.api.itoffers.provider.justjoinit.infrastructure;

import java.net.URL;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
@AllArgsConstructor
public abstract class AbstractJustJoinItHttpConnector implements JustJoinItConnector {
  private final JustJoinItParameters parameters;
  private final HttpConnector httpConnector;

  protected abstract String getRawJsonOffers(Document responseBody);

  public final String fetchStringifyJsonPayload(String technology) {
    Document responseBody = null;

    try {
      responseBody =
          Jsoup.parse(httpConnector.fetchSourceHtml(new URL(parameters.getOffersUrl(technology))));
      return getRawJsonOffers(responseBody);
    } catch (IndexOutOfBoundsException e) {
      throw new JustJoinItException(
          String.format(
              "Empty \"%s\" response: \n\n%s\n", parameters.getOffersUrl(technology), responseBody),
          e);
    } catch (Exception e) {
      throw new JustJoinItException("Error occurred fetching raw HTML", e);
    }
  }
}
