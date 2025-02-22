package pl.api.itoffers.offer.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import pl.api.itoffers.provider.Origin;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Offer {
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Getter
  @NonNull
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "origin_id", referencedColumnName = "id")
  private Origin origin;

  @Getter private final String technology;
  @Getter private final String slug;
  @Getter private final String title;
  @Getter private final String seniority;
  @Embedded @Getter private final Characteristics characteristics;

  @Getter
  @ManyToMany(fetch = FetchType.EAGER)
  private final Set<Category> categories;

  @NonNull
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Salary> salary;

  @Getter @ManyToOne private final Company company;
  @Getter @NonNull private LocalDateTime publishedAt;
  @Getter private final LocalDateTime createdAt;

  public Set<Salary> getSalaries() {
    return salary;
  }

  @Override
  public String toString() {
    return title + " (" + slug + ") " + company.getName() + " publishedAt: " + publishedAt;
  }
}
