package pl.api.itoffers.offer.application.dto.outgoing;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

@Value
@RequiredArgsConstructor
public class OfferDto {
  UUID id;
  String technology;
  String title;
  String link;
  LocalDateTime publishedAt;
  LocalDateTime createdAt;
  UUID companyId;
  String companyName;
  String companyCity;
  String companyStreet;
  Boolean remoteInterview;
  String workplace;
  String time;
  String seniority;
  List<OfferCategoryDto> categories;
  List<OfferSalaryDto> salaries;

  public static OfferDto createFrom(Offer offer, URL link) {
    List<OfferCategoryDto> categoriesDto = new ArrayList<OfferCategoryDto>();
    List<OfferSalaryDto> salariesDto = new ArrayList<OfferSalaryDto>();

    for (Category category : offer.getCategories()) {
      categoriesDto.add(
          new OfferCategoryDto(category.getId(), category.getName(), category.getCreatedAt()));
    }

    for (Salary salary : offer.getSalaries()) {
      salariesDto.add(
          new OfferSalaryDto(
              salary.getAmount().getFrom(),
              salary.getAmount().getTo(),
              salary.getAmount().getCurrency(),
              salary.getEmploymentType(),
              salary.getIsOriginal()));
    }

    return new OfferDto(
        offer.getId(),
        offer.getTechnology(),
        offer.getTitle(),
        link.toString(),
        offer.getPublishedAt(),
        offer.getCreatedAt(),
        offer.getCompany().getId(),
        offer.getCompany().getName(),
        offer.getCompany().getCity(),
        offer.getCompany().getStreet(),
        offer.getCharacteristics().getRemoteInterview(),
        offer.getCharacteristics().getWorkplace(),
        offer.getCharacteristics().getTime(),
        offer.getSeniority(),
        categoriesDto,
        salariesDto);
  }
}
