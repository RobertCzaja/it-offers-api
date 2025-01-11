package pl.api.itoffers.offer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Origin {
  @Id private final String id;
  private final UUID scrappingId;

  @Getter
  @Enumerated(value = EnumType.STRING)
  private final Provider provider;

  public enum Provider {
    JUST_JOIN_IT,
    NO_FLUFF_JOBS
  }
}
