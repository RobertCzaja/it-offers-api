package pl.api.itoffers.offer.application.dto.outgoing;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OffersDto {
  List<OfferDto> list;
}
