package pl.api.itoffers.provider.nofluffjobs.fetcher;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application.provider.no-fluff-jobs")
@Component
@Data
public class NoFluffJobsParameters {
  private static final String LIST_PATH_TEMPLATE = "/pl/{0}?sort=newest";
  private static final String DETAILS_PATH_TEMPLATE = "/pl/job/{0}";
  private String origin;

  public String listPath(String technology) {
    return MessageFormat.format(LIST_PATH_TEMPLATE, technology);
  }

  public URL listUrl(String technology) {
    try {
      return new URL(origin + listPath(technology));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public String detailsPath(String slug) {
    return MessageFormat.format(LIST_PATH_TEMPLATE, slug);
  }

  public URL detailsUrl(String slug) {
    try {
      return new URL(origin + detailsPath(slug));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
