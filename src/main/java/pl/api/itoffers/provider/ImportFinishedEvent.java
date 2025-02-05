package pl.api.itoffers.provider;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ImportFinishedEvent extends ApplicationEvent {
  private final UUID scrapingId;

  public ImportFinishedEvent(Object source, UUID scrapingId) {
    super(source);
    this.scrapingId = scrapingId;
  }
}
