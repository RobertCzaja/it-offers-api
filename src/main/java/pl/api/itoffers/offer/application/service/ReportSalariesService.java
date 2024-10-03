package pl.api.itoffers.offer.application.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSalariesService {

    private final EntityManager em;

    /**
     * todo WIP
     * todo "to" parameter add all of the rest supposed to be passed as method arguments
     * todo Move Criteria to Repository
     */
    public List<OfferDto> getMostPaidOffers() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Offer> query = builder.createQuery(Offer.class);
        Root<Offer> root = query.from(Offer.class);
        Join<Offer, Salary> salary = root.join("salary");
        query.select(root);
        query.where(builder.greaterThanOrEqualTo(salary.get("amount").get("to"), 20000));

        TypedQuery<Offer> typedQuery = em.createQuery(query);
        List<Offer> offers = typedQuery.getResultList();

        List<OfferDto> dtos = new ArrayList<OfferDto>();
        offers.forEach(offer -> dtos.addAll(OfferDto.createFrom(offer)));
        return dtos;
    }
}
