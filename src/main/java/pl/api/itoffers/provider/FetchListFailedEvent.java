package pl.api.itoffers.provider;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class FetchListFailedEvent extends ApplicationEvent {

  public final UUID scrapingId;

  public FetchListFailedEvent(Object source, UUID scrapingId) {
    super(source);
    this.scrapingId = scrapingId;
  }
}
