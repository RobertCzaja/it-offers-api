package pl.api.itoffers.provider;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ImportStartedEvent extends ApplicationEvent {
  private final UUID scrapingId;
  private final List<String> technologies;
  private final String providerName;

  public ImportStartedEvent(
      Object source, UUID scrapingId, List<String> technologies, String providerName) {
    super(source);
    this.scrapingId = scrapingId;
    this.technologies = technologies;
    this.providerName = providerName;
  }
}
