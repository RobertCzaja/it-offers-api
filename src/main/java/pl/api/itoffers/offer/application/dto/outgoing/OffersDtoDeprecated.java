package pl.api.itoffers.offer.application.dto.outgoing;

import java.util.List;
import lombok.*;

/**
 * @deprecated
 */
@Value
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class OffersDtoDeprecated {
  List<OfferDtoDeprecated> list;
}
