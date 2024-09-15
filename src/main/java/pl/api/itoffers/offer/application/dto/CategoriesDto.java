package pl.api.itoffers.offer.application.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CategoriesDto {
    private final Map<String, List<CategoryDto>> list;
}
