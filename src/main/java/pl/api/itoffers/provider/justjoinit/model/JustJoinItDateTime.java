package pl.api.itoffers.provider.justjoinit.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class JustJoinItDateTime {

    public final LocalDateTime value;

    private JustJoinItDateTime(LocalDateTime value) {
        this.value = value;
    }

    /** @param rawDateTime in format yyyy-MM-dd  */
    public static JustJoinItDateTime createFrom(@NotNull String rawDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return new JustJoinItDateTime(
                LocalDateTime.parse(rawDateTime, dateTimeFormatter)
        );
    }
}
