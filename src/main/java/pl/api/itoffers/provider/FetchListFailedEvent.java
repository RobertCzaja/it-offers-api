package pl.api.itoffers.provider;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class FetchListFailedEvent extends ApplicationEvent {

  public final UUID scrapingId;
  public final String technology;
  public final Exception e;

  public FetchListFailedEvent(Object source, UUID scrapingId, String technology, Exception e) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
    this.e = e;
  }
}
