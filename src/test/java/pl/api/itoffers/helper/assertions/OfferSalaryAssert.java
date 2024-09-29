package pl.api.itoffers.helper.assertions;

import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferSalaryAssert {

    public static void isEquals(
        Salary salary,
        String type,
        String currency,
        Integer from,
        Integer to,
        boolean isOriginal
    ) {
        assertThat(salary.getEmploymentType()).isEqualTo(type);
        assertThat(salary.getAmount()).isEqualTo(new SalaryAmount(from,  to, currency));
        assertThat(salary.getIsOriginal()).isEqualTo(isOriginal);
    }
}
