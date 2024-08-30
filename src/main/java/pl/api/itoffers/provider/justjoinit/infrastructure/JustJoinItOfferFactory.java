package pl.api.itoffers.provider.justjoinit.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItOffer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* TODO to remove, it's not used */
@Deprecated
public class JustJoinItOfferFactory {

    public List<JustJoinItOffer> createFromRawPayload(@NotNull Iterator<JsonNode> offersNodes) {
        List<JustJoinItOffer> offers = new LinkedList<>();

        while(offersNodes.hasNext()) {
            JsonNode offerNode = offersNodes.next();

            if (!offerNode.path("niceToHaveSkills").isEmpty()) {
                throw new JustJoinItException("That path should be covered");
            }

            offers.add(
                new JustJoinItOffer(
                    offerNode.path("slug").asText(),
                    offerNode.path("title").asText(),
                    getRequiredSkills(offerNode),
                    offerNode.path("experienceLevel").asText(),
                    Instant.parse(offerNode.path("publishedAt").asText()),
                    offerNode.path("companyName").asText(),
                    getSalaries(offerNode),
                    offerNode.path("workplaceType").asText()
                )
            );
        }
        return offers;
    }

    private @NotNull ArrayList<String> getRequiredSkills(JsonNode offerNode)
    {
        Iterator<JsonNode> requiredSkillsNodes = offerNode.path("requiredSkills").elements();
        ArrayList<String> requiredSkills = new ArrayList<>();
        while (requiredSkillsNodes.hasNext()) {
            requiredSkills.add(requiredSkillsNodes.next().asText());
        }
        return requiredSkills;
    }

    private @NotNull ArrayList<Salary> getSalaries(JsonNode offerNode)
    {
        Iterator<JsonNode> employmentTypesNode = offerNode.path("employmentTypes").elements();
        ArrayList<Salary> salaries = new ArrayList<>();
        while (employmentTypesNode.hasNext()) {
            JsonNode employmentType = employmentTypesNode.next();
            salaries.add(new Salary(
                    employmentType.path("from").asDouble(),
                    employmentType.path("to").asDouble(),
                    employmentType.path("currency").asText().toUpperCase(),
                    employmentType.path("type").asText()
            ));
        }
        return salaries;
    }

}
