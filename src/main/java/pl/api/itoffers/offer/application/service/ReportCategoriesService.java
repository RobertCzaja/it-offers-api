package pl.api.itoffers.offer.application.service;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.outgoing.CategoryDto;
import pl.api.itoffers.offer.application.dto.outgoing.FiltersDto;
import pl.api.itoffers.offer.application.dto.outgoing.TechnologiesCategoriesDto;
import pl.api.itoffers.offer.application.factory.TechnologiesCategoriesDtoFactory;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Offer;

@Service
public class ReportCategoriesService {
  private final OfferRepository offerRepository;

  public ReportCategoriesService(OfferRepository offerRepository) {
    this.offerRepository = offerRepository;
  }

  public CategoriesStatisticsDto computeCategoriesStatistics(
      String[] technologies, LocalDateTime from, LocalDateTime to) {

    HashMap<String, List<CategoryDto>> technologiesWithCategories =
        new HashMap<String, List<CategoryDto>>();

    for (Offer offer : offerRepository.findByCreatedAtBetween(technologies, from, to)) {
      List<CategoryDto> technologyWithCategories =
          technologiesWithCategories.get(offer.getTechnology());

      if (null == technologyWithCategories) {
        technologiesWithCategories.put(
            offer.getTechnology(), TechnologiesCategoriesDtoFactory.create(offer.getCategories()));
      } else {
        offer
            .getCategories()
            .forEach(
                category -> addCategoryToTechnologyCategories(category, technologyWithCategories));
      }
    }
    TechnologiesCategoriesDto technologiesCategoriesDto =
        new TechnologiesCategoriesDto(technologiesWithCategories);
    technologiesCategoriesDto.sort();

    return new CategoriesStatisticsDto(
        new FiltersDto(from, to, technologies), technologiesCategoriesDto);
  }

  /** todo percentage set here doesn't matter 'cause it's recalculated once again while sorting */
  private static void addCategoryToTechnologyCategories(
      Category category, List<CategoryDto> technologyWithCategories) {
    for (CategoryDto technologyCategory : technologyWithCategories) {
      if (technologyCategory.getCategoryName().equals(category.getName())) {
        technologyWithCategories.remove(technologyCategory);
        technologyWithCategories.add(
            technologyCategory.withAddedOccurrence(technologyWithCategories.size()));
        return;
      }
    }
    technologyWithCategories.add(
        new CategoryDto(category.getId(), category.getName(), technologyWithCategories.size()));
  }
}
