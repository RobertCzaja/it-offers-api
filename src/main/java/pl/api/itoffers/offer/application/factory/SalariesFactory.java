package pl.api.itoffers.offer.application.factory;

import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.*;

/** TODO think about moving that class to provider.justjoinit */
@Service
public class SalariesFactory {

    /**
     * TODO finish implementation
     * TODO create more specific UnitTests for that class
     */
    public Set<Salary> create(Offer offer, JustJoinItRawOffer rawOffer) {
        // TODO at this stage Offer Entity needs to contain an ID

        List<LinkedHashMap> employmentTypes = (List<LinkedHashMap>) rawOffer
            .getOffer()
            .getOrDefault("employmentTypes", new ArrayList<>());

        if (employmentTypes.isEmpty()) {
            return new HashSet<Salary>();
        }

        for (LinkedHashMap employmentType : employmentTypes) {
            // todo
        }

        return new HashSet<Salary>();
    }
}
