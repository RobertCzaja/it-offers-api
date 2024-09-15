package pl.api.itoffers.offer.application.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class FiltersDto {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Set<String> technologies;
}
