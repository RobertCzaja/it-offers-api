package pl.api.itoffers.offer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private final UUID offerId;
    @Embedded
    private final SalaryAmount amount;
    private final String employmentType;
    private final Boolean isOriginal;
}
