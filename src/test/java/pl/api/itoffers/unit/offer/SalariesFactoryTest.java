package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.provider.JustJoinItRawOfferBuilder;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SalariesFactoryTest {

    @Test
    public void shouldCreateSalaries() {
        JustJoinItRawOffer rawJJIToffer = JustJoinItRawOfferBuilder.build();

        Set<Salary> salaries = new SalariesFactory().create(rawJJIToffer);

        assertThat(salaries).hasSize(1);
        Salary salary = salaries.iterator().next();
        assertThat(salary.getAmount()).isEqualTo(new SalaryAmount(25000,  32000, "PLN"));
        assertThat(salary.getIsOriginal()).isTrue();
    }

    /*todo when currency will be USD*/
    /*todo when from & to will be null*/
    /*todo when "employmentType" key doesn't exist*/
    /*todo create assertion class for Salary object*/
}
