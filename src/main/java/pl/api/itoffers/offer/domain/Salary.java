package pl.api.itoffers.offer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
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
    @Embedded
    @Getter
    private final SalaryAmount amount;
    @Getter
    private final String employmentType;
    @Getter
    private final Boolean isOriginal;

    public static Salary original(Integer from, Integer to, String currency, String employmentType) {
        return new Salary(
            new SalaryAmount(from, to, currency.toUpperCase()),
            employmentType,
            true
        );
    }

    public static Salary convertedToPLN(Integer from, Integer to, String employmentType) {
        return new Salary(
            new SalaryAmount(from, to, "PLN"),
            employmentType,
            false
        );
    }
}
