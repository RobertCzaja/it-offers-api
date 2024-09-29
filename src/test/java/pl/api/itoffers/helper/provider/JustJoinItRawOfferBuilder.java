package pl.api.itoffers.helper.provider;

import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.time.LocalDateTime;
import java.util.*;

public class JustJoinItRawOfferBuilder {

    private Map<String, Object> data = new LinkedHashMap<String, Object>();

    private void cleanState() {
        data = new LinkedHashMap<String, Object>();
    }

    public JustJoinItRawOfferBuilder emptySalary(String type, String currency) {
        salary(type, currency, null, null, null, null);
        return this;
    }

    public JustJoinItRawOfferBuilder salary(
        String type,
        String currency,
        Integer from,
        Integer to,
        Double fromPln,
        Double toPln
    ) {
        ArrayList<LinkedHashMap> employmentTypes = (ArrayList<LinkedHashMap>) data.get("employmentTypes");
        if (null == employmentTypes) {
            employmentTypes = new ArrayList<LinkedHashMap>();
            data.put("employmentTypes", employmentTypes);
        }
        LinkedHashMap<String, Object> employmentType = new LinkedHashMap<String, Object>();
        employmentType.put("to", to);
        employmentType.put("from", from);
        employmentType.put("type", type);
        employmentType.put("currency", currency);
        employmentType.put("to_pln", toPln);
        employmentType.put("from_pln", fromPln);
        employmentTypes.add(employmentType);
        return this;
    }

    public JustJoinItRawOffer build() {
        JustJoinItRawOffer rawOffer = new JustJoinItRawOffer(
            UUID.randomUUID(),
            "technologyThatDoesNotMatter",
            data,
            LocalDateTime.now()
        );
        cleanState();
        return rawOffer;
    }
}
