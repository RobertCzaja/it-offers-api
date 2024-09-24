package pl.api.itoffers.offer.application.collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.api.itoffers.offer.application.dto.outgoing.CategoryDto;
import pl.api.itoffers.offer.application.dto.outgoing.CategoryDtoList;
import pl.api.itoffers.offer.domain.Category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * New implementation of:
 * @see CategoryDtoList
 */
@RequiredArgsConstructor
public class TechnologyCategories {
    @Getter
    private final List<CategoryDto> list;

    public static TechnologyCategories create(Set<Category> offerCategories) {
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
        return new TechnologyCategories(categories);
    }

    public TechnologyCategories withRecalculatedPercentages() {
        int totalCategoriesCount = allCategoriesCount();
        List<CategoryDto> recalculatedCategories = new ArrayList<CategoryDto>();

        for (CategoryDto categoryDto : list) {
            recalculatedCategories.add(categoryDto.withRecalculatedPercentage(totalCategoriesCount));
        }
        return new TechnologyCategories(recalculatedCategories);
    }

    public void sort() {
        list.sort(new Comparator<CategoryDto>() {
            @Override
            public int compare(CategoryDto dto1, CategoryDto dto2) {
                int countCompare = dto2.getCount().compareTo(dto1.getCount());

                return countCompare != 0
                        ? countCompare
                        : dto1.getCategoryName().compareTo(dto2.getCategoryName());
            }
        });
    }

    private int allCategoriesCount() {
        int totalCategoriesCount = 0;
        for (CategoryDto categoryDto : list) {
            totalCategoriesCount += categoryDto.getCount();
        }
        return totalCategoriesCount;
    }
}
