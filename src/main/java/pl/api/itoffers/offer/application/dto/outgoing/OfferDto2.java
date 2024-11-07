package pl.api.itoffers.offer.application.dto.outgoing;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

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
}
