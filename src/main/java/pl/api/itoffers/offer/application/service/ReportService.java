package pl.api.itoffers.offer.application.service;

import org.springframework.data.util.Pair;
import pl.api.itoffers.offer.application.dto.CategoriesDto;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.CategoryDto;
import pl.api.itoffers.offer.application.dto.FiltersDto;
import pl.api.itoffers.offer.application.repository.OfferRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ReportService {
    private final OfferRepository offerRepository;

    public ReportService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public CategoriesStatisticsDto computeCategoriesStatistics(Pair<LocalDateTime, LocalDateTime> period, Set<String> technologies) {


        HashMap<String, List<CategoryDto>> technologiesWithCategories = new HashMap<String, List<CategoryDto>>();

        return new CategoriesStatisticsDto(
            new FiltersDto(),
            new CategoriesDto(technologiesWithCategories)
        );
    }
}
