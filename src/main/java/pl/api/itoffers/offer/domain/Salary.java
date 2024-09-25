package pl.api.itoffers.offer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Salary {
    @Id
    private final UUID offerId;
    @Id
    private final String currency;
    @Column(name = "amount_from")
    private final Integer from;
    @Column(name = "amount_to")
    private final Integer to;
    private final String employmentType;
    private final Boolean isOriginal;
}
