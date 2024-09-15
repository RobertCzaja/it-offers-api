package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.service.ReportService;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportTest {

    private OfferInMemoryRepository offerRepository;
    private OfferBuilder builder;
    private ReportService reportService;

    @BeforeEach
    public void setUp() {
        this.offerRepository = new OfferInMemoryRepository();
        this.builder = new OfferBuilder();
        this.reportService = new ReportService(this.offerRepository);
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

        CategoriesStatisticsDto dto = reportService.computeCategoriesStatistics(null, null);

        assertThat("").isNotNull(); // todo assertion to change
    }

}
