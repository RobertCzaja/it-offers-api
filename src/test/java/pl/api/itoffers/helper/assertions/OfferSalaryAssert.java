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

    public static void collectionContains(
        Set<Salary> salaries,
        String type,
        String currency,
        Integer from,
        Integer to,
        boolean isOriginal
    ) {
        Object[] salariesArray = salaries.toArray();
        for (Object salaryObject : salariesArray) {
            Salary salary = (Salary) salaryObject;

            if (
                salary.getIsOriginal().equals(isOriginal) &&
                salary.getEmploymentType().equals(type) &&
                salary.getAmount().getCurrency().equals(currency) &&
                salary.getAmount().getFrom().equals(from) &&
                salary.getAmount().getTo().equals(to)
            ) {
                return;
            }

        }
        throw new RuntimeException("Expected Salary does not appear in the given Salaries");
    }
}
