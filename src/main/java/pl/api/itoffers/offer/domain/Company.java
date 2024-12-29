package pl.api.itoffers.offer.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Company {
  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Getter private final String name;
  @Getter private final String city;
  @Getter private final String street;
}
