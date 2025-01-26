package pl.api.itoffers.provider;

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
@Getter
public class Origin {
  @Id private final String id;
  private final UUID scrappingId;

  @Enumerated(value = EnumType.STRING)
  private final Provider provider;

  public enum Provider {
    JUST_JOIN_IT,
    NO_FLUFF_JOBS
  }
}
