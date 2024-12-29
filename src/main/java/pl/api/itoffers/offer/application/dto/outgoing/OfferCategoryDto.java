package pl.api.itoffers.offer.application.dto.outgoing;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class OfferCategoryDto {
  UUID id;
  String name;
  LocalDateTime createdAt;
}
