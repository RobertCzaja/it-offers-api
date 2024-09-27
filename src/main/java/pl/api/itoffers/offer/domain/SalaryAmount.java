package pl.api.itoffers.offer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
public class SalaryAmount {
    @Column(name = "amount_from")
    private final Integer from;
    @Column(name = "amount_to")
    private final Integer to;
    private final String currency;
}
