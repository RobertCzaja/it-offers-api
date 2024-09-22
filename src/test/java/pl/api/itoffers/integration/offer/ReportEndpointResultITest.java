package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import pl.api.itoffers.helper.AbstractITest;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.helper.assertions.ExpectedCategories;
import pl.api.itoffers.helper.assertions.OfferCategoriesAssert;
import pl.api.itoffers.integration.offer.helper.OfferTestManager;
import pl.api.itoffers.integration.offer.helper.ReportEndpointCaller;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
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

    @Test
    public void shouldFilterOffersByDates() {
        builder.job("php").at("08-30").skills("php", "docker").save();
        builder.job("php").at("09-01").skills("php", "mysql", "docker").save();
        builder.job("php").at("09-01").skills("php").save();
        builder.job("php").at("09-02").skills("php", "docker").save();
        builder.job("php").at("09-03").skills("php", "docker", "kubernetes").save();
        builder.job("java").at("08-30").skills("java", "spring").save();
        builder.job("java").at("09-01").skills("java", "maven").save();

        ResponseEntity<CategoriesStatisticsDto> response = reportEndpointCaller.makeRequestForObject("2024-09-01", "2024-09-02");

        // TODO add assertion that "java" technology does not exists
        hasAppliedFilters(response.getBody());
        OfferCategoriesAssert.hasExactCategories("php", response.getBody(), new ExpectedCategories().
            add("php", 50.0, 3).
            add("docker", 33.333333333333336, 2).
            add("mysql", 16.666666666666668, 1)
        );
    }

    private static void hasAppliedFilters(CategoriesStatisticsDto dto) {
        assertThat(dto.getFilters().getDateFrom()).isNotNull();
        assertThat(dto.getFilters().getDateTo()).isNotNull();
        assertThat(dto.getFilters().getTechnologies()).isNull();
    }
}
