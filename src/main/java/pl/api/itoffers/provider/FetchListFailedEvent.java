package pl.api.itoffers.provider;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FetchListFailedEvent extends ApplicationEvent {

  private final UUID scrapingId;

  public FetchListFailedEvent(Object source, UUID scrapingId) {
    super(source);
    this.scrapingId = scrapingId;
  }
}
