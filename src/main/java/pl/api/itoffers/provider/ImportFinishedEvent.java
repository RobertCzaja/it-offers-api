package pl.api.itoffers.provider;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class ImportFinishedEvent extends ApplicationEvent {
  public final UUID scrapingId;

  public ImportFinishedEvent(Object source, UUID scrapingId) {
    super(source);
    this.scrapingId = scrapingId;
  }
}
