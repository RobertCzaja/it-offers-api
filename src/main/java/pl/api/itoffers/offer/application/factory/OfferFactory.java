package pl.api.itoffers.offer.application.factory;

import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.OfferMetadata;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

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

  public static Set<Category> createCategories(JustJoinItRawOffer rawOffer) {
    List<String> requiredSkills = (List<String>) rawOffer.getOffer().get("requiredSkills");
    Set<Category> categories = new HashSet<>();
    for (String requiredSkill : requiredSkills) {
      categories.add(new Category(requiredSkill));
    }
    return categories;
  }

  public static Company createCompany(JustJoinItRawOffer rawOffer) {
    return new Company(
        (String) rawOffer.getOffer().get("companyName"),
        (String) rawOffer.getOffer().get("city"),
        (String) rawOffer.getOffer().get("street"));
  }
}
