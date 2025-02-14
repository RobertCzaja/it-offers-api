package pl.api.itoffers.provider;

import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ImportStartedEvent extends ApplicationEvent {
  public final UUID scrapingId;
  public final List<String> technologies;
  public final String providerName;

  public ImportStartedEvent(
      Object source, UUID scrapingId, List<String> technologies, String providerName) {
    super(source);
    this.scrapingId = scrapingId;
    this.technologies = technologies;
    this.providerName = providerName;
  }
}
