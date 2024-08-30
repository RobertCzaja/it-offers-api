package pl.api.itoffers.offer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;

@Data
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@RequiredArgsConstructor
public class Salary {

    @Column(name = "salary_from")
    private final Double from;
    @Column(name = "salary_to")
    private final Double to;
    @Column(name = "salary_currency")
    private final String currency;
    @Column(name = "salary_employment_type")
    private final String employmentType;
}
