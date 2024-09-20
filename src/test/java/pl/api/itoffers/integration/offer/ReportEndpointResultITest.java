package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.ReportEndpointCaller;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportEndpointResultITest extends AbstractITest {

    @Autowired
    private OfferTestManager offerTestManager;
    @Autowired
    private ReportEndpointCaller reportEndpointCaller;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = new OfferBuilder();
        this.builder.generateId = false;
        offerTestManager.clearAll();
    }

    /* TODO needs to be implemented */
    @Test
    public void ______________() {
        List<Offer> offers = new ArrayList<>();
        offers.add(builder.job("php").skills("php", "mysql", "docker").build());
        offerTestManager.saveAll(offers);

        assertThat("").isNotNull();
    }
}
