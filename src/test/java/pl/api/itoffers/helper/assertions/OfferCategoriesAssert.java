package pl.api.itoffers.helper.assertions;

import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.outgoing.CategoryDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferCategoriesAssert {

    public static void hasAppliedFilters(CategoriesStatisticsDto dto, String[] expectedTechnologies) {
        assertThat(dto.getFilters().getDateFrom()).isNotNull();
        assertThat(dto.getFilters().getDateTo()).isNotNull();
        assertThat(dto.getFilters().getTechnologies()).isEqualTo(expectedTechnologies);
    }

    public static void hasNoResults(CategoriesStatisticsDto dto) {
        assertThat(dto.getResult().getList()).isEmpty();
    }

    public static void hasTechnology(String technologyName, CategoriesStatisticsDto dto) {
        assertThat(dto.getResult().getList().get(technologyName)).isNotNull();
    }

    public static void hasNotTechnology(String technologyName, CategoriesStatisticsDto dto) {
        assertThat(dto.getResult().getList().get(technologyName)).isNull();
    }

    public static void hasExactCategories(
        String technologyName,
        CategoriesStatisticsDto dto,
        ExpectedCategories expectedCategories
    ) {
        assertThat(dto.getResult().getList().get(technologyName).size()).isEqualTo(expectedCategories.categories.size());
        for (int i = 0; i <= (expectedCategories.categories.size() - 1); i++) {
            CategoryDto categoryDto = dto.getResult().getList().get(technologyName).get(i);
            List expectedCategory = expectedCategories.categories.get(i);
            assertThat(categoryDto.getCategoryName()).isEqualTo(expectedCategory.get(0));
            assertThat(categoryDto.getPercentage()).isEqualTo(expectedCategory.get(1));
            assertThat(categoryDto.getCount()).isEqualTo(expectedCategory.get(2));
        }
    }
}
