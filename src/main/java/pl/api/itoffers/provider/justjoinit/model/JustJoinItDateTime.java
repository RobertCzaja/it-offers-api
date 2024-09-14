package pl.api.itoffers.provider.justjoinit.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JustJoinItDateTime {

    public final LocalDateTime value;

    public JustJoinItDateTime(LocalDateTime value) {
        this.value = value;
    }

    /** TODO remove method argument */
    public static JustJoinItDateTime createFrom(String rawDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return new JustJoinItDateTime(
                LocalDateTime.parse("2024-07-27T15:00:38.890Z", dateTimeFormatter)
        );
    }
}
