package pl.api.itoffers.offer.application.dto.incoming;

import pl.api.itoffers.shared.http.exception.ValidationException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


public class DatesRangeFilter {

    private final Date from;
    private final Date to;

    public DatesRangeFilter(Date from, Date to) {
        Date fromFilter = from;
        Date toFilter = to;

        if (null == fromFilter) {
            fromFilter = new Date(100, 0, 1);
        }

        if (null == toFilter) {
            toFilter = new Date();
        }

        if (fromFilter.after(toFilter)) {
            throw ValidationException.becauseOf("dateFrom cannot be greater thant dateTo");
        }

        this.from = fromFilter;
        this.to = toFilter;
    }

    public LocalDateTime getFrom() {
        return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime getTo() {
        return to
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            .toLocalDate().atTime(23,59,59, 999999999);
    }
}
