package pl.api.itoffers.provider.nofluffjobs.fetcher.details;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import java.util.Map;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.shared.utils.json.JsonNodeMapper;

@Service
public class OfferDetailsFromJsonPayloadExtractor {

  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonNodeMapper jsonNodeMapper = new JsonNodeMapper();

  public Map<String, Object> extractOffer(String rawJsonPayload) {

    if (null == rawJsonPayload || rawJsonPayload.isEmpty()) {
      throw NoFluffJobsException.payloadIsEmpty();
    }

    try {
      JsonNode node = mapper.readTree(rawJsonPayload);

      Iterator<JsonNode> graphElements = node.get("@graph").elements();

      while (graphElements.hasNext()) {
        JsonNode element = graphElements.next();

        if (element.get("@type").textValue().equals("JobPosting")) {
          Map<String, Object> offerDetails = jsonNodeMapper.mapToHash(element);
          offerDetails.remove("applicantLocationRequirements");
          return offerDetails;
        }
      }

      throw NoFluffJobsException.onIncompleteStructure(rawJsonPayload, "@type: JobPosting");

    } catch (JsonProcessingException | NullPointerException e) {
      throw NoFluffJobsException.onExtractingOffers(rawJsonPayload, e);
    }
  }
}
