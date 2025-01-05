package pl.api.itoffers.provider.nofluffjobs;

import java.io.IOException;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
@AllArgsConstructor
public class NoFluffJobsConnector {

  private final HttpConnector httpConnector;
  private final NoFluffJobsParameters parameters;

  public String fetchStringifyJsonPayload(String technology) {
    try {
      return Jsoup.parse(httpConnector.fetchSourceHtml(parameters.listUrl(technology)))
          .select("body")
          .last()
          .children()
          .last()
          .html();
    } catch (IOException e) {
      throw NoFluffJobsException.onFetchingHtmlPage(e);
    }
  }
}
