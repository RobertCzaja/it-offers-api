package pl.api.itoffers.provider.nofluffjobs;

import java.io.IOException;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
@AllArgsConstructor
public class NoFluffJobsConnector {

  private final HttpConnector httpConnector;
  private final NoFluffJobsParameters parameters;

  public String fetchStringifyJsonPayload(String technology) {
    URL listPath = parameters.listUrl("php");

    try {
      String htmlPage = httpConnector.fetchSourceHtml(listPath);

      int a = 1;
    } catch (IOException e) {
      throw new RuntimeException(e); // todo add custom exception
    }

    // todo add implementation
    return "";
  }
}
