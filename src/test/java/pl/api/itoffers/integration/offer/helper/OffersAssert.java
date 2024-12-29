package pl.api.itoffers.integration.offer.helper;

import pl.api.itoffers.helper.LocalDateTimeCustomBuilder;
import pl.api.itoffers.offer.application.dto.outgoing.OfferCategoryDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class OffersAssert {
    public static void hasExactOffers(List<List> expected, OffersDto offersDto) {
        for (int i = 0; i <= (expected.size() - 1); i++) {
            OfferDto dto = offersDto.getList().get(i);
            List expectedData = expected.get(i);

            assertThat(dto.getTechnology()).isEqualTo(expectedData.get(0));
            hasExpectedCategoriesWithNotSpecifiedOrder((List<String>) expectedData.get(1), dto.getCategories());
            assertThat(dto.getPublishedAt()).isEqualTo(LocalDateTimeCustomBuilder.createFromDate((String) expectedData.get(2)));
        }
    }

    private static void hasExpectedCategoriesWithNotSpecifiedOrder(
        List<String> expectedCategoriesNames,
        List<OfferCategoryDto> offerCategories
    ) {
        ArrayList<String> expectedCategories = expectedCategoriesNames.stream().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<String> actualCategories = new ArrayList<String>();
        for (OfferCategoryDto offerCategory : offerCategories) {
            actualCategories.add(offerCategory.getName());
        }

        Collections.sort(expectedCategories);
        Collections.sort(actualCategories);

        assertThat(actualCategories).isEqualTo(expectedCategories);
    }
}
