package pl.api.itoffers.offer.application.dto.outgoing;

import pl.api.itoffers.offer.domain.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
}