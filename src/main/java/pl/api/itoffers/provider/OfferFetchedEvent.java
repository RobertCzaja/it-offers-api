package pl.api.itoffers.provider;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OfferFetchedEvent extends ApplicationEvent {
  private final UUID scrapingId;
  private final String technology;

  public OfferFetchedEvent(Object source, UUID scrapingId, String technology) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
  }
}
