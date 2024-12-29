package pl.api.itoffers.offer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Category {
  @Getter
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Getter
  @Column(unique = true)
  @NotNull
  private final String name;

  @CreationTimestamp @Getter private LocalDateTime createdAt;
}
