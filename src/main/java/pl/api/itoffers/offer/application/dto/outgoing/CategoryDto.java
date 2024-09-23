package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CategoryDto {
    private final UUID categoryId;
    private final String categoryName;
    private final Double percentage;
    private final Integer count;

    public CategoryDto(UUID categoryId, String categoryName, int totalCategoriesCount) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.count = 1;
        this.percentage = (double) (this.count * 100 / totalCategoriesCount);
    }

    public CategoryDto withAddedOccurrence(int totalCategoriesCount) {
        int newCount = count + 1;
        return new CategoryDto(
            categoryId,
            categoryName,
            ((double) newCount * 100 / totalCategoriesCount),
            newCount
        );
    }

    public CategoryDto withRecalculatedPercentage(int totalCategoriesCount) {
        return new CategoryDto(
                categoryId,
                categoryName,
                ((double) count * 100 / totalCategoriesCount),
                count
        );
    }
}
