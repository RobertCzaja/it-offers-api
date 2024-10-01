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
    public void shouldReturnMostTopPaidJobs() {
        this.builder.job("php").skills("php").pln(15000, 18000).save();
        this.builder.job("php").skills("php").pln(17000, 21000).save();
        this.builder.job("php").skills("php").pln(18000, 23000).save();
        this.builder.job("java").skills("java").pln(17000, 19000).save();
        this.builder.job("java").skills("java").pln(18000, 24000).save();
        this.builder.job("java").skills("java").pln(21500, 26000).save();

        // todo add implementation
    }
}
