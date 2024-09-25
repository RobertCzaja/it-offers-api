package pl.api.itoffers.offer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Salary {
    @EmbeddedId
    private final SalaryId id;
    @Column(name = "amount_from")
    private final Integer from; // todo move it to embedded object: Amount
    @Column(name = "amount_to")
    private final Integer to; // todo move it to embedded object: Amount
    private final String employmentType;
    private final Boolean isOriginal;
}
