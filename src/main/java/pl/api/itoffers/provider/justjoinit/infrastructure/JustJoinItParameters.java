package pl.api.itoffers.provider.justjoinit.infrastructure;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import pl.api.itoffers.provider.ProviderException;

@ConfigurationProperties(prefix = "application.provider.just-join-it")
@Component
@Data
public class JustJoinItParameters {
  private static final String DETAILS_PATH = "/job-offer/";
  private static final String LIST_PATH = "/job-offers/all-locations/";
  private String origin;

  public String getOffersUrl(String technology) {
    return this.getOrigin().toString() + getOffersPath(technology);
  }

  public String getOffersPath(String technology) {
    return LIST_PATH + technology;
  }

  public URL getOrigin() {
    try {
      return new URI(origin).toURL();
    } catch (MalformedURLException | URISyntaxException e) {
      throw ProviderException.couldNotCreateUrl(e);
    }
  }

  public URL getOfferUrl(String slug) {
    try {
      return new URI(origin + DETAILS_PATH + slug).toURL();
    } catch (MalformedURLException | URISyntaxException e) {
      throw ProviderException.couldNotCreateUrl(e);
    }
  }
}
