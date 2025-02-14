package pl.api.itoffers.provider;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class FetchDetailsFailedEvent extends ApplicationEvent {
  public final UUID scrapingId;

  public FetchDetailsFailedEvent(Object source, UUID scrapingId) {
    super(source);
    this.scrapingId = scrapingId;
  }
}
