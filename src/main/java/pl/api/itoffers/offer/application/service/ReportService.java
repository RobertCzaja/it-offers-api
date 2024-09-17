package pl.api.itoffers.offer.application.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.CategoriesDto;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.CategoryDto;
import pl.api.itoffers.offer.application.dto.FiltersDto;
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

    public CategoriesStatisticsDto computeCategoriesStatistics(Pair<LocalDateTime, LocalDateTime> period, Set<String> technologies) {

        HashMap<String, List<CategoryDto>> technologiesWithCategories = new HashMap<String, List<CategoryDto>>();

        for (Offer offer : offerRepository.fetch(/*todo pass arguments here*/)) {
            List<CategoryDto> technologyWithCategories = technologiesWithCategories.get(offer.getTechnology());

            if (null == technologyWithCategories) {
                technologiesWithCategories.put(offer.getTechnology(), createInitialCategories(offer.getCategories()));
            } else {
                updateExistingTechnologyCategories(offer.getCategories(), technologyWithCategories);
            }

            sort(technologiesWithCategories);
        }

        return new CategoriesStatisticsDto(
            new FiltersDto(),
            new CategoriesDto(technologiesWithCategories)
        );
    }

    public static List<CategoryDto> createInitialCategories(Set<Category> offerCategories) {
        List<CategoryDto> categories = new ArrayList<CategoryDto>();
        int technologyCategoriesCount = 0;
        for (Category category: offerCategories) {
            technologyCategoriesCount++;
            categories.add(
                new CategoryDto(
                    category.getId(),
                    category.getName(),
                    technologyCategoriesCount
                )
            );
        }
        return categories;
    }

    public static void updateExistingTechnologyCategories(
        Set<Category> offerCategories,
        List<CategoryDto> technologyWithCategories
    ) {
        for (Category category: offerCategories ) {
            CategoryDto alreadyAddedCategory = null;
            Integer alreadyAddedDtoIndex = null;
            int technologyCategoriesCount = 0;
            int index = -1;

            for (CategoryDto technologyCategory : technologyWithCategories) {
                index++;
                technologyCategoriesCount++;
                if (null == alreadyAddedCategory && technologyCategory.getCategoryName().equals(category.getName()) /* TODO think about switch to compare Category Id */) {
                    alreadyAddedCategory = technologyCategory;
                    alreadyAddedDtoIndex = index;
                }
            }

            if (null == alreadyAddedCategory) {
                technologyWithCategories.add(
                    new CategoryDto(
                        category.getId(),
                        category.getName(),
                        technologyCategoriesCount
                    )
                );
            } else {
                technologyWithCategories.remove(alreadyAddedDtoIndex.intValue());
                technologyWithCategories.add(alreadyAddedCategory.withAddedOccurrence(technologyCategoriesCount));
            }
        }
    }

    public static void sort(HashMap<String, List<CategoryDto>> technologiesWithCategories) {
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
