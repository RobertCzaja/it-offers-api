package pl.api.itoffers.offer.application.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoriesStatisticsDto {
    private final FiltersDto filters;
    private final CategoriesDto result;
}
