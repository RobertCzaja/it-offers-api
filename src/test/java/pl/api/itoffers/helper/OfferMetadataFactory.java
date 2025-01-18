package pl.api.itoffers.helper;

import java.time.LocalDateTime;
import pl.api.itoffers.offer.domain.OfferMetadata;

public class OfferMetadataFactory {
  public static OfferMetadata create(String title) {
    return new OfferMetadata(
        "java",
        "some java slug",
        title,
        "senior",
        "remote",
        "full_time",
        false,
        LocalDateTime.now());
  }
}
