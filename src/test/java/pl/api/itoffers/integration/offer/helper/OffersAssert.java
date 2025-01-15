package pl.api.itoffers.integration.offer.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import pl.api.itoffers.helper.LocalDateTimeCustomBuilder;
import pl.api.itoffers.helper.assertions.OfferSalaryAssert;
import pl.api.itoffers.offer.application.dto.outgoing.OfferCategoryDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.offer.domain.Offer;

public class OffersAssert {

  public record ExpectedSalary(
      String expectedType,
      String expectedCurrency,
      int expectedFrom,
      int expectedTo,
      Boolean expectedIsOriginal) {}

  public static void hasExpectedOfferModel(
      Offer offer,
      String expectedTechnology,
      String expectedTitle,
      String expectedSlug,
      String expectedCompanyName,
      int expectedCategoriesSize,
      LocalDateTime expectedPublishedAt,
      ExpectedSalary... expectedSalaries) {
    assertThat(offer.getTechnology()).isEqualTo(expectedTechnology);
    assertThat(offer.getTitle()).isEqualTo(expectedTitle);
    assertThat(offer.getSlug()).isEqualTo(expectedSlug);
    assertThat(offer.getCompany().getName()).isEqualTo(expectedCompanyName);
    assertThat(offer.getCategories()).hasSize(expectedCategoriesSize);
    assertThat(offer.getPublishedAt()).isEqualTo(expectedPublishedAt);
    assertThat(offer.getSalaries().size()).isEqualTo(expectedSalaries.length);
    for (ExpectedSalary expectedSalary : expectedSalaries) {
      OfferSalaryAssert.collectionContains(
          offer.getSalaries(),
          expectedSalary.expectedType,
          expectedSalary.expectedCurrency,
          expectedSalary.expectedFrom,
          expectedSalary.expectedTo,
          expectedSalary.expectedIsOriginal);
    }
  }

  public static void hasExactOffers(List<List> expected, OffersDto offersDto) {
    for (int i = 0; i <= (expected.size() - 1); i++) {
      OfferDto dto = offersDto.getList().get(i);
      List expectedData = expected.get(i);

      assertThat(dto.getTechnology()).isEqualTo(expectedData.get(0));
      hasExpectedCategoriesWithNotSpecifiedOrder(
          (List<String>) expectedData.get(1), dto.getCategories());
      assertThat(dto.getPublishedAt())
          .isEqualTo(LocalDateTimeCustomBuilder.createFromDate((String) expectedData.get(2)));
    }
  }

  private static void hasExpectedCategoriesWithNotSpecifiedOrder(
      List<String> expectedCategoriesNames, List<OfferCategoryDto> offerCategories) {
    ArrayList<String> expectedCategories =
        expectedCategoriesNames.stream().collect(Collectors.toCollection(ArrayList::new));
    ArrayList<String> actualCategories = new ArrayList<String>();
    for (OfferCategoryDto offerCategory : offerCategories) {
      actualCategories.add(offerCategory.getName());
    }

    Collections.sort(expectedCategories);
    Collections.sort(actualCategories);

    assertThat(actualCategories).isEqualTo(expectedCategories);
  }
}
