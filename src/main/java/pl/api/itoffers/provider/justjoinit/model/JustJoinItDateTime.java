package pl.api.itoffers.provider.justjoinit.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JustJoinItDateTime {

    private static final String DEFAULT_DATE_TIME = "2024-07-27T15:00:38.890Z";
    public final LocalDateTime value;

    public JustJoinItDateTime(LocalDateTime value) {
        this.value = value;
    }

    public static JustJoinItDateTime createFrom() {
        return createFrom(DEFAULT_DATE_TIME);
    }

    public static JustJoinItDateTime createFrom(@NotNull String rawDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return new JustJoinItDateTime(
                LocalDateTime.parse(rawDateTime, dateTimeFormatter)
        );
    }
}
