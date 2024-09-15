package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferCalculationITest {

    private OfferInMemoryRepository offerRepository;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        this.offerRepository = new OfferInMemoryRepository();
        this.builder = new OfferBuilder();
    }

    /*
     *  TODO use DataProvider
     *  TODO under the development
     */
    @Test
    public void shouldReturnExpectedStatistics() {

        List<Offer> offers = new ArrayList<>();
        offers.add(builder.job("php").skills("php", "mysql", "docker").build());
        offers.add(builder.job("php").skills("php", "docker").build());
        offers.add(builder.job("php").skills("php 8.2", "kubernetes").build());
        offers.add(builder.job("php").skills("php", "mysql").build());
        offers.add(builder.job("php").skills("php", "postgres", "phpunit", "ci/cd").build());
        offers.add(builder.job("java").skills("java", "postgres", "maven").build());
        offers.add(builder.job("java").skills("java", "no sql", "gradle", "AWS").build());
        offers.add(builder.job("java").skills("java 17", "hibernate").build());
        offerRepository.offers = offers;

        assertThat("").isNotNull();
    }

}
