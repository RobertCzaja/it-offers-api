package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferCalculationITest {

    private OfferBuilder offerBuilder;

    @BeforeEach
    public void setUp() {
        this.offerBuilder = new OfferBuilder();
    }

    /*
     *  TODO use DataProvider
     *  TODO move to unit directory
     *   TODO under the development
     */
    @Test
    public void shouldReturnExpectedStatistics() {

        List<Offer> offers = new ArrayList<>();
        offers.add(offerBuilder.job("php").skills("php", "mysql", "docker").build());
        offers.add(offerBuilder.job("java").skills("java", "postgres", "maven").build());

        assertThat("").isNotNull();
    }

}
