package pl.api.itoffers.helper.provider;

import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.time.LocalDateTime;
import java.util.*;

public class JustJoinItRawOfferBuilder {
    public static JustJoinItRawOffer build() {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        List<LinkedHashMap> employmentTypes1 = new ArrayList<LinkedHashMap>();
        LinkedHashMap<String, Object> employmentType1a = new LinkedHashMap<String, Object>();
        employmentType1a.put("to", 32000);
        employmentType1a.put("from", 25000);
        employmentType1a.put("type", "b2b");
        employmentType1a.put("currency", "pln");
        employmentType1a.put("to_pln", "32000");
        employmentType1a.put("from_pln", "25000");
        employmentTypes1.add(employmentType1a);
        data.put("employmentTypes", employmentTypes1);

        return new JustJoinItRawOffer(
            UUID.randomUUID(),
            "technologyThatDoesNotMatter",
            data,
            LocalDateTime.now()
        );
    }
}
