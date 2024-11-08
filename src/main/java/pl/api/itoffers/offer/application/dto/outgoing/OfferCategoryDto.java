package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@RequiredArgsConstructor
public class OfferCategoryDto {
    UUID id;
    String name;
    LocalDateTime createdAt;
}
