package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.assertions.OfferSalaryAssert;
import pl.api.itoffers.helper.provider.JustJoinItRawOfferBuilder;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        OfferSalaryAssert.collectionContains(salaries, "b2b", "EUR", 4500, 7000, true);
        OfferSalaryAssert.collectionContains(salaries, "b2b", "PLN", 19256, 29955, false);
    }

    @Test
    public void shouldReturnNoSalariesWhenRawDataAreEmpty() {
        JustJoinItRawOffer rawJJITOffer = builder.emptySalary("b2b", "pln").build();

        Set<Salary> salaries = salariesFactory.create(rawJJITOffer);

        assertThat(salaries).isEmpty();
    }

    @Test
    public void shouldCreateSalaryCollectionWhenThereIsMultipleJJITEmploymentTypes() {
        JustJoinItRawOffer rawJJITOffer = builder
            .salary("b2b", "usd", 4699, 3132, 12000D, 18000D)
            .salary("permanent", "pln", 12000, 18000, 12000D, 18000D)
            .build();

        Set<Salary> salaries = salariesFactory.create(rawJJITOffer);

        assertThat(salaries).hasSize(3);
        OfferSalaryAssert.collectionContains(salaries, "b2b", "PLN", 12000, 18000, false);
        OfferSalaryAssert.collectionContains(salaries, "b2b", "USD", 4699, 3132, true);
        OfferSalaryAssert.collectionContains(salaries, "permanent", "PLN", 12000, 18000, true);
    }

    @Test
    public void shouldAbortExecutionWhenThereIsNoKeyThatHoldsTheEmploymentDataReturnedFromJJITProvider() {
        JustJoinItRawOffer rawJJITOffer = builder.build();
        assertThrows(RuntimeException.class, () -> salariesFactory.create(rawJJITOffer));
    }
}
