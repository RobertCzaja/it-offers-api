package pl.api.itoffers.provider.nofluffjobs;

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
  private String origin;

  public URL listPatch(String technology) {
    try {
      return new URL(origin + MessageFormat.format(LIST_PATH_TEMPLATE, technology));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
