package pl.api.itoffers.provider.nofluffjobs;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.shared.http.connector.HttpConnector;

@Service
@AllArgsConstructor
public class NoFluffJobsConnector {

  private final HttpConnector httpConnector;
  private final NoFluffJobsParameters parameters;

  private String fetchStringifyJsonOffers(String technology) {
    // todo add implementation
    return "";
  }
}
