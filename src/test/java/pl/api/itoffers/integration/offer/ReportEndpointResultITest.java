package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.ReportEndpointCaller;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportEndpointResultITest extends AbstractITest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private OfferTestManager offerTestManager;
    @Autowired
    private ReportEndpointCaller reportEndpointCaller;
    private OfferBuilder builder;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.builder = new OfferBuilder(categoryRepository, companyRepository, offerRepository);
        this.builder.generateId = false;
        offerTestManager.clearAll();
    }

    /* TODO needs to be implemented */
    @Test
    public void ______________() {
        builder.job("php").skills("php", "mysql", "docker").save();
        builder.job("php").skills("php", "mysql", "docker").save();
        assertThat("").isNotNull();
    }
}
