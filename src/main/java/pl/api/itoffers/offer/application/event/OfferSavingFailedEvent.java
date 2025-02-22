package pl.api.itoffers.offer.application.event;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

@SuppressWarnings("PMD.DataClass")
public class OfferSavingFailedEvent extends ApplicationEvent {
  public UUID scrapingId;
  public String technology;
  public String originId;
  public Exception e;

  public OfferSavingFailedEvent(
      Object source, UUID scrapingId, String technology, String originId, Exception e) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
    this.originId = originId;
    this.e = e;
  }
}
