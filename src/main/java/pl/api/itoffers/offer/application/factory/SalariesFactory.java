package pl.api.itoffers.offer.application.factory;

import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.*;

/** TODO think about moving that class to provider.justjoinit */
@Service
public class SalariesFactory {

    /**
     * TODO finish implementation
     * TODO create more specific UnitTests for that class
     * TODO at this stage Offer Entity needs to contain an ID
     * TODO testing data should contain on offer with default USD currency but recalculated to PLN
     */
    public Set<Salary> create(Offer offer, JustJoinItRawOffer rawOffer) {
        Set<Salary> salaries = new HashSet<Salary>();
        List<LinkedHashMap> employmentTypes = (List<LinkedHashMap>) rawOffer.getOffer().get("employmentTypes");

        if (null == employmentTypes) {
            return new HashSet<Salary>();
        }

        employmentTypes.forEach(employmentType -> salaries.addAll(createSalaries(offer.getId(), employmentType)));

        return salaries;
    }

    public Set<Salary> createSalaries(UUID offerId, LinkedHashMap employmentType) {
        Set<Salary> salaries = new HashSet<Salary>();
        Integer to = (Integer) employmentType.get("to");
        Integer from = (Integer) employmentType.get("from");
        String currency = (String) employmentType.get("currency");
        boolean isPln = currency.equalsIgnoreCase("pln");

        if (null == to || null == from) {
            return salaries;
        }

        salaries.add(Salary.original(offerId, from, to, currency, (String) employmentType.get("type")));

        if (!isPln) {
            Double plnTo = (Double) employmentType.get("to_pln");
            Double plnFrom = (Double) employmentType.get("from_pln");

            if (null != plnTo && null != plnFrom) {
                salaries.add(Salary.convertedToPLN(offerId, plnFrom.intValue(), plnTo.intValue(), (String) employmentType.get("type")));
            }
        }

        return salaries;
    }
}
