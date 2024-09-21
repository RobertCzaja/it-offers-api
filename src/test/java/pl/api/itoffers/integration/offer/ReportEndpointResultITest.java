package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public void shouldFilterOffersByDates() {
        builder.job("php").at("08-30").skills("php", "docker").save();
        builder.job("php").at("09-01").skills("php", "mysql", "docker").save();
        builder.job("php").at("09-02").skills("php", "git").save();
        builder.job("php").at("09-03").skills("php", "docker", "kubernetes").save();
        builder.job("java").at("08-30").skills("java", "spring").save();
        builder.job("java").at("09-01").skills("java", "maven").save();

        // todo change to specific object type as generic type
        ResponseEntity<String> response = reportEndpointCaller.makeRequest("2024-09-01", "2024-09-02");

        assertThat("").isNotNull();
    }
}
