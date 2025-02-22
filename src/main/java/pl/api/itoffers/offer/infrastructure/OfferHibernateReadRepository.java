package pl.api.itoffers.offer.infrastructure;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.application.dto.outgoing.OfferSalaries.OfferSalariesDto;
import pl.api.itoffers.offer.application.repository.OfferReadRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.ProviderFacade;

@Repository
@RequiredArgsConstructor
public class OfferHibernateReadRepository implements OfferReadRepository {

  private final EntityManager em;
  private final ProviderFacade providerFacade;

  public List<OfferSalariesDto> getBySalary(
      int amountTo,
      String currency,
      String employmentType,
      List<String> technologies,
      LocalDateTime from,
      LocalDateTime to) {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Offer> query = builder.createQuery(Offer.class);
    Root<Offer> offerRoot = query.from(Offer.class);
    Join<Offer, Salary> salary = offerRoot.join("salary");
    CriteriaBuilder.In<String> technologyInClause = builder.in(offerRoot.get("technology"));
    for (String technology : technologies) {
      technologyInClause.value(technology);
    }
    query.select(offerRoot);
    query.where(
        builder.and(
            builder.greaterThanOrEqualTo(salary.get("amount").get("to"), amountTo),
            builder.equal(salary.get("employmentType"), employmentType),
            builder.equal(salary.get("amount").get("currency"), currency),
            builder.between(offerRoot.get("createdAt"), from, to),
            technologyInClause));

    TypedQuery<Offer> typedQuery = em.createQuery(query);
    List<Offer> offers = typedQuery.getResultList();

    List<OfferSalariesDto> dtos = new ArrayList<>();
    offers.forEach(
        offer -> dtos.addAll(OfferSalariesDto.createFrom(offer, currency, employmentType)));
    sortDesc(dtos);
    return dtos;
  }

  @Override
  public List<OfferDto> getList(List<String> technologies, LocalDateTime from, LocalDateTime to) {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    CriteriaQuery<Offer> query = builder.createQuery(Offer.class);
    Root<Offer> offerRoot = query.from(Offer.class);
    CriteriaBuilder.In<String> technologyInClause = builder.in(offerRoot.get("technology"));
    for (String technology : technologies) {
      technologyInClause.value(technology);
    }
    query.select(offerRoot);
    query.where(
        builder.and(builder.between(offerRoot.get("publishedAt"), from, to), technologyInClause));
    query.orderBy(builder.desc(offerRoot.get("publishedAt")));

    TypedQuery<Offer> typedQuery = em.createQuery(query);
    List<Offer> offers = typedQuery.getResultList();

    List<OfferDto> offersDto = new ArrayList<>();
    for (Offer offer : offers) {
      offersDto.add(OfferDto.createFrom(offer, providerFacade.getOfferUrl(offer)));
    }

    return offersDto;
  }

  private static void sortDesc(List<OfferSalariesDto> offers) {
    offers.sort(
        new Comparator<OfferSalariesDto>() {
          @Override
          public int compare(OfferSalariesDto dto1, OfferSalariesDto dto2) {
            return dto2.amountTo().compareTo(dto1.amountTo());
          }
        });
  }
}
