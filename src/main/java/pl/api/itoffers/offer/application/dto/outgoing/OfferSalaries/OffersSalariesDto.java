package pl.api.itoffers.offer.application.dto.outgoing.OfferSalaries;

import java.util.List;
import lombok.*;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OffersSalariesDto {
  List<OfferSalariesDto> list;
}
