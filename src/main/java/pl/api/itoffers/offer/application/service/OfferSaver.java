package pl.api.itoffers.offer.application.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.domain.Category;

/** todo add integration tests */
@Service
@RequiredArgsConstructor
public class OfferSaver {

  private final CategoryRepository categoryRepository;

  public void save(Set<Category> categories) {

    var categoriesForEntity = new HashSet<Category>();
    var categoriesToSave = new HashSet<Category>();

    for (Category newCategory : categories) {
      Category category = categoryRepository.findByName(newCategory.getName());

      if (null == category) {
        category = newCategory;
        categoriesToSave.add(category);
      }

      categoriesForEntity.add(category);
    }
    var CategoryCollections = new CategoryCollections(categoriesForEntity, categoriesToSave);

    // todo wip
  }
}
