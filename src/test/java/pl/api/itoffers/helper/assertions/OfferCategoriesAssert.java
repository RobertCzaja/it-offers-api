package pl.api.itoffers.helper.assertions;

import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.CategoryDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OfferCategoriesAssert {

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
