package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.assertions.OfferSalaryAssert;
import pl.api.itoffers.helper.provider.JustJoinItRawOfferBuilder;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SalariesFactoryTest {

    private JustJoinItRawOfferBuilder builder;
    private SalariesFactory salariesFactory;

    @BeforeEach
    public void setUp() {
        this.builder = new JustJoinItRawOfferBuilder();
        this.salariesFactory = new SalariesFactory();
    }

    @Test
    public void shouldCreateSalaries() {
        JustJoinItRawOffer rawJJITOffer = builder
            .salary("b2b", "pln", 25000, 32000, 25000D, 32000D)
            .build();

        Set<Salary> salaries = salariesFactory.create(rawJJITOffer);

        assertThat(salaries).hasSize(1);
        OfferSalaryAssert.isEquals(salaries, "b2b", "PLN", 25000, 32000, true);
    }

    @Test
    public void whenSalaryHasDifferentCurrencyThanPLNItShouldCreatedSalaryInOriginalCurrencyAndInPLN() {
        JustJoinItRawOffer rawJJITOffer = builder
            .salary("b2b", "eur", 4500, 7000, 19256.852, 29955.102)
            .build();

        Set<Salary> salaries = salariesFactory.create(rawJJITOffer);

        assertThat(salaries).hasSize(2);
        OfferSalaryAssert.isEquals(salaries, 0, "b2b", "EUR", 4500, 7000, true);
        OfferSalaryAssert.isEquals(salaries, 1, "b2b", "PLN", 19256, 29955, false);
    }

    /*todo when from & to will be null*/
    /*todo it can contains multiple employmentTypes*/
    /*todo when "employmentType" key doesn't exist*/
    /*todo create assertion class for Salary object*/
}
