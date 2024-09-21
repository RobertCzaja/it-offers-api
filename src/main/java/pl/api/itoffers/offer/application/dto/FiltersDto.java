package pl.api.itoffers.offer.application.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class FiltersDto {
    private final LocalDateTime dateFrom;
    private final LocalDateTime dateTo;
    private final String[] technologies;
}
