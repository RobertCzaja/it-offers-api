package pl.api.itoffers.offer.application.dto.incoming;

import lombok.Data;
import pl.api.itoffers.shared.http.exception.ValidationException;

import java.util.Date;

@Data
public class CategoriesFilter {

    private final String[] technologies;
    private final Date from;
    private final Date to;

    public CategoriesFilter(String[] technologies, Date from, Date to) {

        if (null == from) {
            from = new Date(100, 0, 1);
        }

        if (null == to) {
            to = new Date();
        }

        if (from.after(to)) {
            throw ValidationException.becauseOf("dateFrom cannot be greater thant dateTo");
        }

        this.technologies = technologies;
        this.from = from;
        this.to = to;
    }
}
