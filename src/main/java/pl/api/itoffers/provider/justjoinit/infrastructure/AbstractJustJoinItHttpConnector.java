package pl.api.itoffers.provider.justjoinit.infrastructure;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.JustJoinItConnector;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
public abstract class AbstractJustJoinItHttpConnector implements JustJoinItConnector {
  @Autowired private JustJoinItParameters parameters;

  protected abstract String getRawJsonOffers(Document responseBody);

  public final String fetchStringifyJsonPayload(String technology) {
    Document responseBody = null;

    try {
      responseBody = Jsoup.parse(fetchSourceHtml(technology));
      return getRawJsonOffers(responseBody);
    } catch (IndexOutOfBoundsException e) {
      throw new JustJoinItException(
          String.format(
              "Empty \"%s\" response: \n\n%s\n", parameters.getOffersUrl(technology), responseBody),
          e);
    } catch (Exception e) {
      e.printStackTrace();
      throw new JustJoinItException("Error occurred fetching raw HTML", e);
    }
  }

  /**
   * @deprecated todo extract to another class with Shared namespace
   * @see HttpConnector
   */
  private String fetchSourceHtml(String technology) throws IOException {
    Scanner scanner = null;
    try {
      URLConnection connection = new URL(parameters.getOffersUrl(technology)).openConnection();
      scanner = new Scanner(connection.getInputStream());
      scanner.useDelimiter("\\Z");
      return scanner.next();
    } finally {
      if (null != scanner) {
        scanner.close();
      }
    }
  }
}
