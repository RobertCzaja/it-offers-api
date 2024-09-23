package pl.api.itoffers.offer.application.service;

import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.outgoing.*;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Offer;

import java.time.LocalDateTime;
import java.util.*;

/* TODO think about refactor of that class */
@Service
public class ReportService {
    private final OfferRepository offerRepository;

    public ReportService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public CategoriesStatisticsDto computeCategoriesStatistics(LocalDateTime from, LocalDateTime to, String[] technologies) {

        HashMap<String, List<CategoryDto>> technologiesWithCategories = new HashMap<String, List<CategoryDto>>();

        for (Offer offer : offerRepository.findByCreatedAtBetween(from, to, technologies)) {
            List<CategoryDto> technologyWithCategories = technologiesWithCategories.get(offer.getTechnology());

            if (null == technologyWithCategories) {
                technologiesWithCategories.put(offer.getTechnology(), CategoryDtoList.create(offer.getCategories()));
            } else {
                offer.getCategories().forEach(category -> addCategoryToTechnologyCategories(category, technologyWithCategories));
            }

            sort(technologiesWithCategories);
        }

        return new CategoriesStatisticsDto(
            new FiltersDto(from, to, technologies),
            new CategoriesDto(technologiesWithCategories)
        );
    }

    /** todo percentage set here doesn't matter 'cause it's recalculated once again while sorting */
    private static void addCategoryToTechnologyCategories(Category category, List<CategoryDto> technologyWithCategories) {
        for (CategoryDto technologyCategory : technologyWithCategories) {
            if (technologyCategory.getCategoryName().equals(category.getName())) {
                technologyWithCategories.remove(technologyCategory);
                technologyWithCategories.add(technologyCategory.withAddedOccurrence(technologyWithCategories.size()));
                return;
            }
        }
        technologyWithCategories.add(new CategoryDto(category.getId(), category.getName(), technologyWithCategories.size()));
    }

    private static void sort(HashMap<String, List<CategoryDto>> technologiesWithCategories) {
        for (String technology : technologiesWithCategories.keySet()) {
            List<CategoryDto> technologyCategories = technologiesWithCategories.get(technology);
            int totalCategoriesCount = 0;
            for (CategoryDto categoryDto : technologyCategories) {
                totalCategoriesCount += categoryDto.getCount();
            }
            List<CategoryDto> recalculatedCategories = new ArrayList<CategoryDto>();
            for (CategoryDto categoryDto : technologyCategories) {
                recalculatedCategories.add(categoryDto.withRecalculatedPercentage(totalCategoriesCount));
            }

            recalculatedCategories.sort(new Comparator<CategoryDto>() {
                @Override
                public int compare(CategoryDto dto1, CategoryDto dto2) {
                    return dto2.getCount().compareTo(dto1.getCount());
                }
            });

            technologiesWithCategories.put(technology, recalculatedCategories);
        }
    }
}
