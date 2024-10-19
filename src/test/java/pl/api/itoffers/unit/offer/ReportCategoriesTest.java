package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.helper.assertions.ExpectedCategories;
import pl.api.itoffers.helper.assertions.OfferCategoriesAssert;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.service.ReportCategoriesService;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.List;

public class ReportCategoriesTest {

    private OfferInMemoryRepository offerRepository;
    private OfferBuilder builder;
    private ReportCategoriesService reportCategoriesService;

    @BeforeEach
    public void setUp() {
        this.offerRepository = new OfferInMemoryRepository();
        this.builder = new OfferBuilder();
        this.reportCategoriesService = new ReportCategoriesService(this.offerRepository);
    }

    @Test
    public void shouldReturnExpectedStatistics() {
        List<Offer> offers = new ArrayList<>();
        offers.add(builder.job("php").skills("php", "mysql").build());
        offers.add(builder.job("php").skills("php", "docker").build());
        offers.add(builder.job("php").skills("php", "kubernetes", "docker").build());
        offers.add(builder.job("php").skills("php", "mysql").build());
        offers.add(builder.job("php").skills("php", "docker").build());
        offers.add(builder.job("java").skills("java", "postgres", "maven").build());
        offers.add(builder.job("java").skills("java", "postgres").build());
        offers.add(builder.job("java").skills("java", "hibernate").build());
        offers.add(builder.job("java").skills("java", "postgres", "hibernate").build());
        offerRepository.offers = offers;

        CategoriesStatisticsDto dto = reportCategoriesService.computeCategoriesStatistics(null, null, null);

        OfferCategoriesAssert.hasExactCategories("java", dto, new ExpectedCategories().
            add("java", 40.0, 4).
            add("postgres", 30.0, 3).
            add("hibernate", 20.0, 2).
            add("maven", 10.0, 1)
        );
        OfferCategoriesAssert.hasExactCategories("php", dto, new ExpectedCategories().
            add("php", 45.45454545454545, 5).
            add("docker", 27.272727272727273, 3).
            add("mysql", 18.181818181818183, 2).
            add("kubernetes", 9.090909090909092, 1)
        );
    }
}
