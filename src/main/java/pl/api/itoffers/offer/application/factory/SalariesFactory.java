package pl.api.itoffers.offer.application.factory;

import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.*;

/** TODO think about moving that class to provider.justjoinit */
@Service
public class SalariesFactory {

    public Set<Salary> create(JustJoinItRawOffer rawOffer) {
        Set<Salary> salaries = new HashSet<Salary>();
        List<LinkedHashMap> employmentTypes = (List<LinkedHashMap>) rawOffer.getOffer().get("employmentTypes");

        if (null == employmentTypes) {
            throw new RuntimeException("employmentTypes key does not exist");
        }

        employmentTypes.forEach(employmentType -> salaries.addAll(createSalaries(employmentType)));

        return salaries;
    }

    /**
     * TODO some of that logic could be encapsulated in Offer Entity
     */
    private Set<Salary> createSalaries(LinkedHashMap employmentType) {
        Set<Salary> salaries = new HashSet<Salary>();
        Integer to = (Integer) employmentType.get("to");
        Integer from = (Integer) employmentType.get("from");
        String currency = (String) employmentType.get("currency");
        boolean isPln = "pln".equalsIgnoreCase(currency);

        if (null == to || null == from) {
            return salaries;
        }

        salaries.add(Salary.original(from, to, currency, (String) employmentType.get("type")));

        if (!isPln) {
            Double plnTo = toDouble(employmentType.get("to_pln"));
            Double plnFrom = toDouble(employmentType.get("from_pln"));

            if (null != plnTo && null != plnFrom) {
                salaries.add(Salary.convertedToPLN(plnFrom.intValue(), plnTo.intValue(), (String) employmentType.get("type")));
            }
        }

        return salaries;
    }

    private static Double toDouble(Object amount) {
        return (amount instanceof String)
            ? Double.parseDouble((String) amount)
            : Double.valueOf(String.valueOf(amount));
    }
}
