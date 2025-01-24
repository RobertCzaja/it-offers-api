package pl.api.itoffers.offer.application.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.exception.DuplicatedOfferException;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Characteristics;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.OfferMetadata;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferSaver {

  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;
  private final OfferRepository offerRepository;
  private final ClockInterface clock;

  public void save(
      Origin origin,
      OfferMetadata offerMetadata,
      Set<Category> categories,
      Set<Salary> salaries,
      Company company) {
    try {
      var offer = prepareAndSave(origin, offerMetadata, categories, salaries, company);
      log.info(
          "[{}][{}] '{}' from {} at {}",
          origin.getProvider().name(),
          offer.getTechnology(),
          offer.getTitle(),
          offer.getCompany().getName(),
          offer.getPublishedAt());
    } catch (DuplicatedOfferException ignored) {
    } catch (Exception e) {
      log.error(
          "Error on saving {} offer ({}) in scrapping: {}",
          origin.getProvider().name(),
          origin.getId(),
          origin.getScrappingId());
    }
  }

  private Offer prepareAndSave(
      Origin origin,
      OfferMetadata offerMetadata,
      Set<Category> categories,
      Set<Salary> salaries,
      Company company) {
    var categoryCollections = prepareCategories(categories);
    var preparedCompany = prepareCompany(company);

    var offer =
        new Offer(
            origin,
            offerMetadata.technology(),
            offerMetadata.slug(),
            offerMetadata.title(),
            offerMetadata.seniority(),
            new Characteristics(
                offerMetadata.workplace(), offerMetadata.time(), offerMetadata.remoteInterview()),
            categoryCollections.forEntity(),
            salaries,
            preparedCompany,
            offerMetadata.publishedAt(),
            clock.now());

    Offer alreadyStoredOffer = findAlreadyStoredOffer(offer);

    if (null != alreadyStoredOffer) {
      throw new DuplicatedOfferException();
    }

    companyRepository.save(preparedCompany);
    categoryRepository.saveAll(categoryCollections.toSave());
    offerRepository.save(offer);
    return offer;
  }

  private Offer findAlreadyStoredOffer(Offer offer) {
    return offerRepository.findByDifferentOffer(
        offer.getSlug(), offer.getTitle(), offer.getCompany().getName());
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
