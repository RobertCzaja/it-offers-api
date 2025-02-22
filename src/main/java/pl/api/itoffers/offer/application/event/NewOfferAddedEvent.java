package pl.api.itoffers.offer.application.event;

import java.net.URL;
import java.util.UUID;
import org.springframework.context.ApplicationEvent;

@SuppressWarnings("PMD.DataClass")
public class NewOfferAddedEvent extends ApplicationEvent {
  public final UUID scrapingId;
  public final String technology;
  public final URL url;
  public final String title;
  public final String salaryCurrency;
  public final Integer salaryFrom;
  public final Integer salaryTo;

  public NewOfferAddedEvent(
      Object source,
      UUID scrapingId,
      String technology,
      URL url,
      String title,
      String salaryCurrency,
      Integer salaryFrom,
      Integer salaryTo) {
    super(source);
    this.scrapingId = scrapingId;
    this.technology = technology;
    this.url = url;
    this.title = title;
    this.salaryCurrency = salaryCurrency;
    this.salaryFrom = salaryFrom;
    this.salaryTo = salaryTo;
  }
}
