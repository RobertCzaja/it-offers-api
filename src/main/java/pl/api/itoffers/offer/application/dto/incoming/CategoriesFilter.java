package pl.api.itoffers.offer.application.dto.incoming;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;

public class CategoriesFilter {

  @Getter private final String[] technologies;
  private final DatesRangeFilter datesRange;

  public CategoriesFilter(String[] technologies, Date from, Date to) {
    this.technologies = technologies;
    this.datesRange = new DatesRangeFilter(from, to);
  }

  public LocalDateTime getFrom() {
    return this.datesRange.getFrom();
  }

  public LocalDateTime getTo() {
    return this.datesRange.getTo();
  }
}
