package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.integration.offer.helper.ReportEndpointCaller;
import static org.assertj.core.api.Assertions.assertThat;

public class ReportEndpointResultITest extends AbstractITest {

    @Autowired
    private ReportEndpointCaller reportEndpointCaller;

    @BeforeEach
    public void setUp() {
        super.setUp();
        // TODO clare tables state
        // TODO add to offer/category/company tables ect. few Offers
    }

    /* TODO needs to be implemented */
    @Test
    public void ______________() {
        assertThat("").isNotNull();
    }

}
