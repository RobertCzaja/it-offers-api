package pl.api.itoffers.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jetbrains.annotations.NotNull;

public final class LocalDateTimeCustomBuilder {

  private LocalDateTimeCustomBuilder() {}

  /**
   * @param rawDate in format yyyy-MM-dd
   */
  public static LocalDateTime createFromDate(@NotNull String rawDate) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    return LocalDateTime.parse(rawDate + "T12:00:01.001Z", dateTimeFormatter);
  }
}
