package pl.api.itoffers.offer.application.dto.outgoing;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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
    this.percentage = countPercentage(this.count, totalCategoriesCount);
  }

  public CategoryDto withAddedOccurrence(int totalCategoriesCount) {
    int newCount = count + 1;
    return new CategoryDto(
        categoryId, categoryName, countPercentage(newCount, totalCategoriesCount), newCount);
  }

  public CategoryDto withRecalculatedPercentage(int totalCategoriesCount) {
    return new CategoryDto(
        categoryId, categoryName, countPercentage(count, totalCategoriesCount), count);
  }

  private static double countPercentage(int count, int totalCount) {
    return (double) count * 100 / totalCount;
  }
}
