package pl.api.itoffers.offer.application.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.dto.outgoing.OfferDto;
import pl.api.itoffers.offer.domain.Salary;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportSalariesService {

    private final EntityManager em;

    /**
     * todo WIP
     * todo Move Criteria to Repository
     */
    public List<OfferDto> getMostPaidOffers() {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Salary> criteriaQuery = criteriaBuilder.createQuery(Salary.class);
        Root<Salary> root = criteriaQuery.from(Salary.class);
        criteriaQuery.select(root);

        TypedQuery<Salary> query = em.createQuery(criteriaQuery);
        query.getResultList();

        List<OfferDto> offers = new ArrayList<OfferDto>();
        offers.add(new OfferDto(15000,17000,"PLN", "php", "some title", "some url"));
        return offers;
    }
}
