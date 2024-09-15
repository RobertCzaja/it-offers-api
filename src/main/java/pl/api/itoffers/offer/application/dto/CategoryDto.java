package pl.api.itoffers.offer.application.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CategoryDto {
    private final Integer categoryId;
    private final String categoryName;
    private final Double percentage;
    private final Integer count;
}
