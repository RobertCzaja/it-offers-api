package pl.api.itoffers.offer.application.event;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OfferSavingFailedEvent extends ApplicationEvent {
  private UUID scrapingId;
  private String technology;

  public OfferSavingFailedEvent(Object source, UUID scrapingId, String technology) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
  }
}
