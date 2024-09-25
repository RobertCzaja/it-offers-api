package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class TechnologiesCategoriesDto {
    private final Map<String, List<CategoryDto>> list; /* TODO change name to map */

    public void sort() {
        for (String technology : list.keySet()) {
            List<CategoryDto> recalculated = recalculatedPercentages(technology);

            recalculated.sort(new Comparator<CategoryDto>() {
                @Override
                public int compare(CategoryDto dto1, CategoryDto dto2) {
                    int countCompare = dto2.getCount().compareTo(dto1.getCount());

                    return countCompare != 0
                        ? countCompare
                        : dto1.getCategoryName().compareTo(dto2.getCategoryName());
                }
            });

            list.put(technology, recalculated);
        }
    }

    private List<CategoryDto> recalculatedPercentages(String technology) {
        int totalCategoriesCount = countAllCategories(technology);
        List<CategoryDto> recalculatedCategories = new ArrayList<CategoryDto>();

        for (CategoryDto categoryDto : list.get(technology)) {
            recalculatedCategories.add(categoryDto.withRecalculatedPercentage(totalCategoriesCount));
        }
        return recalculatedCategories;
    }

    private int countAllCategories(String technology) {
        List<CategoryDto> technologyCategories = list.get(technology);
        int totalCategoriesCount = 0;
        for (CategoryDto categoryDto : technologyCategories) {
            totalCategoriesCount += categoryDto.getCount();
        }
        return totalCategoriesCount;
    }
}
