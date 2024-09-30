package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;

public class ReportSalaryEndpointResultITest extends AbstractITest {

    @Autowired
    private OfferTestManager offerTestManager;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = offerTestManager.createOfferBuilder();
        this.builder.generateId = false;
        offerTestManager.clearAll();
    }

    @Test
    public void should_____________________() {
        // todo add implementation
    }
}
