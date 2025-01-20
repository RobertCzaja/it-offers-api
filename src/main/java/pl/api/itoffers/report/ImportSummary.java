package pl.api.itoffers.report;

import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated
 */
public class ImportSummary {

  private final Map<String, Integer> newOffersCounter = new HashMap<>();

  public static ImportSummary create() {
    return new ImportSummary();
  }

  public void newOfferAdded(String technology) {
    if (newOffersCounter.containsKey(technology)) {
      var count = (Integer) newOffersCounter.get(technology);
      newOffersCounter.replace(technology, ++count);
    } else {
      newOffersCounter.put(technology, 1);
    }
  }

  public String getReport() {
    var sb = new StringBuilder();
    newOffersCounter.forEach(
        (technology, counter) -> {
          sb.append(String.format("%s: %s \n", technology, counter));
        });
    return sb.toString();
  }
}
