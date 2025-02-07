package pl.api.itoffers.offer.application.event;

import java.net.URL;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewOfferAddedEvent extends ApplicationEvent {
  private final UUID scrapingId;
  private final String technology;
  private final URL url;
  private final String title;
  private final List<String> categories;
  private final String salaryCurrency;
  private final Integer salaryFrom;
  private final Integer salaryTo;

  public NewOfferAddedEvent(
      Object source,
      UUID scrapingId,
      String technology,
      URL url,
      String title,
      List<String> categories,
      String salaryCurrency,
      Integer salaryFrom,
      Integer salaryTo) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
    this.url = url;
    this.title = title;
    this.categories = categories;
    this.salaryCurrency = salaryCurrency;
    this.salaryFrom = salaryFrom;
    this.salaryTo = salaryTo;
  }
}
