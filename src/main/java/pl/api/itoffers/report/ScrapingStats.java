package pl.api.itoffers.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/** TODO write unit test */
public class ScrapingStats {

  private final String provider;
  private final LocalDateTime startedAt;
  private LocalDateTime endedAt;
  private final Map<String, Integer> newOffersCounter = new HashMap<>();

  // todo stats about MongoDB objects, how much of these
  // todo ranking of the most payed 10 jobs (with full URLs)
  // todo full URLs to all new offers for technologies php, java

  public ScrapingStats(LocalDateTime now, String provider) {
    this.startedAt = now;
    this.provider = provider;
  }

  public void newOfferAdded(String technology) {
    if (newOffersCounter.containsKey(technology)) {
      var count = (Integer) newOffersCounter.get(technology);
      newOffersCounter.replace(technology, ++count);
    } else {
      newOffersCounter.put(technology, 1);
    }
  }

  public String finish(LocalDateTime now) {
    this.endedAt = now;
    return createReport();
  }

  private String createReport() {
    var sb = new StringBuilder();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm:ss");
    sb.append(String.format("Provider: %s", provider));
    sb.append(String.format("Started at: %s", startedAt.format(formatter)));
    sb.append(String.format("Finished at: %s", endedAt.format(formatter)));
    // todo calculate how long the scraping takes
    newOffersCounter.forEach(
        (technology, counter) -> {
          sb.append(String.format("%s: %s \n", technology, counter));
        });
    return sb.toString();
  }
}
