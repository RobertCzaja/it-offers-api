package pl.api.itoffers.unit.offer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.CategoryDto;
import pl.api.itoffers.offer.application.service.ReportService;
import pl.api.itoffers.offer.domain.Offer;

import java.util.ArrayList;
import java.util.HashMap;
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

    /* TODO to refactor - it's ugly! */
    @Test
    public void shouldReturnExpectedStatistics() {

        List<Offer> offers = new ArrayList<>();
        offers.add(builder.job("php").skills("php", "mysql", "docker").build());
        offers.add(builder.job("php").skills("php", "docker").build());
        offers.add(builder.job("php").skills("php", "kubernetes").build());
        offers.add(builder.job("php").skills("php", "mysql").build());
        offers.add(builder.job("php").skills("php", "postgres", "phpunit", "ci/cd").build());
        offers.add(builder.job("java").skills("java", "postgres", "maven").build());
        offers.add(builder.job("java").skills("java", "postgres").build());
        offers.add(builder.job("java").skills("java", "hibernate").build());
        offers.add(builder.job("java").skills("java", "postgres", "hibernate").build());
        offerRepository.offers = offers;

        CategoriesStatisticsDto dto = reportService.computeCategoriesStatistics(null, null);

        assertThat(dto.getResult().getList().get("java").size()).isEqualTo(4);
        assertThat(dto.getResult().getList().get("php").size()).isEqualTo(7);


        HashMap<String, HashMap<Double, Integer>> expected = new HashMap<String, HashMap<Double, Integer>>();

        List<List> java = List.of(
            List.of("java", 40.0, 4),
            List.of("postgres", 30.0, 3),
            List.of("hibernate", 20.0, 2),
            List.of("maven", 10.0, 1)
        );
        List<List> php = List.of(
            List.of("php", 38.46153846153846, 5),
            List.of("docker", 15.384615384615385, 2),
            List.of("mysql", 15.384615384615385, 2),
            List.of("kubernetes", 7.6923076923076925, 1),
            List.of("phpunit", 7.6923076923076925, 1),
            List.of("postgres", 7.6923076923076925, 1),
            List.of("ci/cd", 7.6923076923076925, 1)
        );

        for (int i = 0; i <= 3; i++) {
            CategoryDto categoryDto = dto.getResult().getList().get("java").get(i);
            List expectedCategory = java.get(i);
            assertThat(categoryDto.getCategoryName()).isEqualTo(expectedCategory.get(0));
            assertThat(categoryDto.getPercentage()).isEqualTo(expectedCategory.get(1));
            assertThat(categoryDto.getCount()).isEqualTo(expectedCategory.get(2));
        }


        for (int i = 0; i <= 3; i++) {
            CategoryDto categoryDto = dto.getResult().getList().get("php").get(i);
            List expectedCategory = php.get(i);
            assertThat(categoryDto.getCategoryName()).isEqualTo(expectedCategory.get(0));
            assertThat(categoryDto.getPercentage()).isEqualTo(expectedCategory.get(1));
            assertThat(categoryDto.getCount()).isEqualTo(expectedCategory.get(2));
        }
    }
}
