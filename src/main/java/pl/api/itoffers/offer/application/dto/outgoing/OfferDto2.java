package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** todo name to change #59 */
@Value
@RequiredArgsConstructor
public class OfferDto2 {
    /*
     * todo #59 add
     *  Offer uuid
     *  publishedAt
     *  createdAt
     *  requiredSkills/categories {name, id, createdAt}
     *  companyData - name/city/street
     *  seniorityLevel
     *  remoteInterview
     *  time
     *  workplace
     */
    //Integer amountFrom;
    //Integer amountTo;
    //String currency;
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

    public static OfferDto2 createFrom(Offer offer, URL link) {
        List<OfferCategoryDto> categoriesDto = new ArrayList<OfferCategoryDto>();

        for (Category category : offer.getCategories()) {
            categoriesDto.add(new OfferCategoryDto(category.getId(), category.getName(), category.getCreatedAt()));
        }

        return new OfferDto2(
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
            categoriesDto
        );
    }
}
