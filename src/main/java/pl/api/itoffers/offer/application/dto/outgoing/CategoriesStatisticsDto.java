package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoriesStatisticsDto {
  private final FiltersDto filters;
  private final TechnologiesCategoriesDto result;
}
