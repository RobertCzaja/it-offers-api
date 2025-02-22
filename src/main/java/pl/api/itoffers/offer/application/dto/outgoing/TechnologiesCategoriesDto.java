package pl.api.itoffers.offer.application.dto.outgoing;

import java.util.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import pl.api.itoffers.offer.application.factory.TechnologiesCategoriesDtoFactory;
import pl.api.itoffers.offer.domain.Offer;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class TechnologiesCategoriesDto {
  private final Map<String, List<CategoryDto>> map;

  public void add(Offer offer) {
    map.put(offer.getTechnology(), TechnologiesCategoriesDtoFactory.create(offer.getCategories()));
  }

  public void sort() {
    for (String technology : map.keySet()) {
      List<CategoryDto> recalculated = recalculatedPercentages(technology);

      recalculated.sort(
          Comparator.comparing(CategoryDto::getCount, Comparator.reverseOrder())
              .thenComparing(CategoryDto::getCategoryName));

      map.put(technology, recalculated);
    }
  }

  private List<CategoryDto> recalculatedPercentages(String technology) {
    int totalCategoriesCount = countAllCategories(technology);
    List<CategoryDto> recalculatedCategories = new ArrayList<>();

    for (CategoryDto categoryDto : map.get(technology)) {
      recalculatedCategories.add(categoryDto.withRecalculatedPercentage(totalCategoriesCount));
    }
    return recalculatedCategories;
  }

  private int countAllCategories(String technology) {
    List<CategoryDto> technologyCategories = map.get(technology);
    int totalCategoriesCount = 0;
    for (CategoryDto categoryDto : technologyCategories) {
      totalCategoriesCount += categoryDto.getCount();
    }
    return totalCategoriesCount;
  }
}
