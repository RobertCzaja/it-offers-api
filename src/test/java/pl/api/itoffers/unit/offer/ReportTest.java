package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.helper.assertions.OfferCategoriesAssert;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.service.ReportService;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.List;

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

    /* TODO Bug https://github.com/RobertCzaja/it-offers/issues/41 */
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

        CategoriesStatisticsDto dto = reportService.computeCategoriesStatistics(null, null, null);

        OfferCategoriesAssert.hasExactCategories("java", dto, List.of(
            List.of("java", 40.0, 4),
            List.of("postgres", 30.0, 3),
            List.of("hibernate", 20.0, 2),
            List.of("maven", 10.0, 1)
        ));
        OfferCategoriesAssert.hasExactCategories("php", dto, List.of(
            List.of("php", 45.45454545454545, 5),
            List.of("docker", 27.272727272727273, 3),
            List.of("mysql", 18.181818181818183, 2),
            List.of("kubernetes", 9.090909090909092, 1)
        ));
    }
}
