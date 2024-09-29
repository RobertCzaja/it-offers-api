package pl.api.itoffers.helper.assertions;

import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferSalaryAssert {

    public static void isEquals(
        Set<Salary> salaries,
        String type,
        String currency,
        Integer from,
        Integer to,
        boolean isOriginal
    ) {
        assertThat(salaries).hasSize(1);
        Object[] salariesArray = salaries.toArray();
        Salary salary = (Salary) salariesArray[0];
        assertThat(salary.getEmploymentType()).isEqualTo(type);
        assertThat(salary.getAmount()).isEqualTo(new SalaryAmount(from,  to, currency));
        assertThat(salary.getIsOriginal()).isEqualTo(isOriginal);
    }

    public static void isEquals(
        Set<Salary> salaries,
        int salaryIndex,
        String type,
        String currency,
        Integer from,
        Integer to,
        boolean isOriginal
    ) {
        Object[] salariesArray = salaries.toArray();
        Salary salary = (Salary) salariesArray[salaryIndex];
        assertThat(salary.getEmploymentType()).isEqualTo(type);
        assertThat(salary.getAmount()).isEqualTo(new SalaryAmount(from,  to, currency));
        assertThat(salary.getIsOriginal()).isEqualTo(isOriginal);
    }
}
