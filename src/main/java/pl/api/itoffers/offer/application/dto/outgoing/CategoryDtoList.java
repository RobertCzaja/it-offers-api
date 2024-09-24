package pl.api.itoffers.offer.application.dto.outgoing;

import pl.api.itoffers.offer.application.collection.TechnologyCategories;
import pl.api.itoffers.offer.domain.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @deprecated use rather:
 * @see TechnologyCategories
 * TODO that class needs to be removed
 */
public class CategoryDtoList {
    public static List<CategoryDto> create(Set<Category> offerCategories) {
        List<CategoryDto> categories = new ArrayList<CategoryDto>();
        int technologyCategoriesCount = 0;
        for (Category category: offerCategories) {
            technologyCategoriesCount++;
            categories.add(
                new CategoryDto(
                    category.getId(),
                    category.getName(),
                    technologyCategoriesCount
                )
            );
        }
        return categories;
    }

    private static int countAllCategories(List<CategoryDto> technologyCategories) {
        int totalCategoriesCount = 0;
        for (CategoryDto categoryDto : technologyCategories) {
            totalCategoriesCount += categoryDto.getCount();
        }
        return totalCategoriesCount;
    }

    public static List<CategoryDto> recalculatedCategories(List<CategoryDto> technologyCategories) {
        int totalCategoriesCount = CategoryDtoList.countAllCategories(technologyCategories);
        List<CategoryDto> recalculatedCategories = new ArrayList<CategoryDto>();

        for (CategoryDto categoryDto : technologyCategories) {
            recalculatedCategories.add(categoryDto.withRecalculatedPercentage(totalCategoriesCount));
        }
        return recalculatedCategories;
    }

    public static void sort(List<CategoryDto> technologyCategories) {
        technologyCategories.sort(new Comparator<CategoryDto>() {
            @Override
            public int compare(CategoryDto dto1, CategoryDto dto2) {
                int countCompare = dto2.getCount().compareTo(dto1.getCount());

                return countCompare != 0
                    ? countCompare
                    : dto1.getCategoryName().compareTo(dto2.getCategoryName());
            }
        });
    }
}
