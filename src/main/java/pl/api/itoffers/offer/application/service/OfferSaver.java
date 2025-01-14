package pl.api.itoffers.offer.application.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Salary;

@Service
@RequiredArgsConstructor
public class OfferSaver {

  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;

  // private final OfferFactory offerFactory; // todo it has ClockInterface which is problematic to
  // inject by Spring - to figure out!

  /**
   * todo under the development, base on:
   *
   * @see OfferService
   */
  public void save(Set<Category> categories, Set<Salary> salaries, Company company) {

    var categoryCollections = prepareCategories(categories);
    var preparedCompany = prepareCompany(company);

    // todo create offer object
    // todo check if it isn't already exist in DB
    // todo if soo abort execution
    // todo save company
    // todo save categories
    // todo save offer
  }

  private CategoryCollections prepareCategories(Set<Category> categories) {
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
    return new CategoryCollections(categoriesForEntity, categoriesToSave);
  }

  private Company prepareCompany(Company newCompany) {
    Company savedCompany = companyRepository.findByName(newCompany.getName());
    return null == savedCompany ? newCompany : savedCompany;
  }
}
