package pl.api.itoffers.offer.application.factory;

import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.service.CategoryCollections;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Characteristics;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.OfferMetadata;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

/**
 * TODO base on
 *
 * @see pl.api.itoffers.provider.nofluffjobs.factory.OfferFactory TODO apply the same interface for
 *     these two factories for different providers TODO since the these cannot be static anymore
 */
@Service
@AllArgsConstructor
public class OfferFactory {
  private final CompanyRepository companyRepository;
  private final CategoryRepository categoryRepository;

  public static Origin createOrigin(JustJoinItRawOffer rawOffer) {
    return new Origin(
        rawOffer.getId().toString(), rawOffer.getScrapingId(), Origin.Provider.JUST_JOIN_IT);
  }

  public static OfferMetadata createOfferMetadata(JustJoinItRawOffer rawOffer) {
    return new OfferMetadata(
        rawOffer.getTechnology(),
        (String) rawOffer.getOffer().get("slug"),
        (String) rawOffer.getOffer().get("title"),
        (String) rawOffer.getOffer().get("experienceLevel"),
        (String) rawOffer.getOffer().get("workplaceType"),
        (String) rawOffer.getOffer().get("workingTime"),
        (Boolean) rawOffer.getOffer().get("remoteInterview"),
        JustJoinItDateTime.createFrom((String) rawOffer.getOffer().get("publishedAt")).value);
  }

  public static Set<Category> createCategoriesNew(JustJoinItRawOffer rawOffer) {
    List<String> requiredSkills = (List<String>) rawOffer.getOffer().get("requiredSkills");
    Set<Category> categories = new HashSet<>();
    for (String requiredSkill : requiredSkills) {
      categories.add(new Category(requiredSkill));
    }
    return categories;
  }

  public static Company createCompanyNew(JustJoinItRawOffer rawOffer) {
    return new Company(
        (String) rawOffer.getOffer().get("companyName"),
        (String) rawOffer.getOffer().get("city"),
        (String) rawOffer.getOffer().get("street"));
  }

  /**
   * @deprecated
   */
  public Offer createOffer(
      JustJoinItRawOffer rawOffer,
      Set<Category> categories,
      Set<Salary> salaries,
      Company company) {
    return new Offer(
        new Origin(
            rawOffer.getId().toString(), rawOffer.getScrapingId(), Origin.Provider.JUST_JOIN_IT),
        rawOffer.getTechnology(),
        (String) rawOffer.getOffer().get("slug"),
        (String) rawOffer.getOffer().get("title"),
        (String) rawOffer.getOffer().get("experienceLevel"),
        createCharacteristics(rawOffer),
        categories,
        salaries,
        company,
        JustJoinItDateTime.createFrom((String) rawOffer.getOffer().get("publishedAt")).value,
        LocalDateTime.now());
  }

  /**
   * @deprecated
   */
  public CategoryCollections createCategories(JustJoinItRawOffer rawOffer) {
    List<String> requiredSkills = (List<String>) rawOffer.getOffer().get("requiredSkills");
    Set<Category> categories = new HashSet<>();
    Set<Category> categoriesToSave = new HashSet<>();

    for (String requiredSkill : requiredSkills) {
      Category category = categoryRepository.findByName(requiredSkill);

      if (null == category) {
        category = new Category(requiredSkill);
        categoriesToSave.add(category);
      }
      categories.add(category);
    }
    return new CategoryCollections(categories, categoriesToSave);
  }

  /**
   * @deprecated
   */
  public Company createCompany(JustJoinItRawOffer rawOffer) {
    String companyName = (String) rawOffer.getOffer().get("companyName");

    Company company = companyRepository.findByName(companyName);
    if (null == company) {
      company =
          new Company(
              companyName,
              (String) rawOffer.getOffer().get("city"),
              (String) rawOffer.getOffer().get("street"));
    }

    return company;
  }

  /**
   * @deprecated
   */
  private Characteristics createCharacteristics(JustJoinItRawOffer rawOffer) {
    return new Characteristics(
        (String) rawOffer.getOffer().get("workplaceType"),
        (String) rawOffer.getOffer().get("workingTime"),
        (Boolean) rawOffer.getOffer().get("remoteInterview"));
  }
}
