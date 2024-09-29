package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.assertions.OfferSalaryAssert;
import pl.api.itoffers.helper.provider.JustJoinItRawOfferBuilder;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SalariesFactoryTest {

    private JustJoinItRawOfferBuilder builder;

    @BeforeEach
    public void setUp() {
        this.builder = new JustJoinItRawOfferBuilder();
    }

    @Test
    public void shouldCreateSalaries() {
        JustJoinItRawOffer rawJJIToffer = builder
            .salary("b2b", "pln", 25000, 32000, "25000", "32000")
            .build();

        Set<Salary> salaries = new SalariesFactory().create(rawJJIToffer);

        assertThat(salaries).hasSize(1);
        OfferSalaryAssert.isEquals(salaries.iterator().next(), "b2b", "PLN", 25000, 32000, true);
    }

    /*todo when currency will be USD*/
    /*todo when from & to will be null*/
    /*todo when "employmentType" key doesn't exist*/
    /*todo create assertion class for Salary object*/
}
