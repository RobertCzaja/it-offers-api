package pl.api.itoffers.provider;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class OfferFetchedEvent extends ApplicationEvent {
  public final UUID scrapingId;
  public final String technology;

  public OfferFetchedEvent(Object source, UUID scrapingId, String technology) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
  }
}
