package pl.api.itoffers.offer.application.dto.outgoing;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FiltersDto {
  private final LocalDateTime dateFrom;
  private final LocalDateTime dateTo;
  private final String[] technologies;
}
