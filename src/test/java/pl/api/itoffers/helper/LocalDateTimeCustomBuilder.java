package pl.api.itoffers.helper;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeCustomBuilder {

    private LocalDateTimeCustomBuilder() {
    }

    /** @param rawDate in format yyyy-MM-dd  */
    public static LocalDateTime createFromDate(@NotNull String rawDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(rawDate+"T12:00:01.001Z", dateTimeFormatter);
    }

}
