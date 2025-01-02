package pl.api.itoffers.provider.nofluffjobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import pl.api.itoffers.provider.justjoinit.service.extractor.OffersPayloadMapper;

/** TODO name of the class to figure out */
public class PayloadFromJsonExtractor {

  private final ObjectMapper mapper = new ObjectMapper();
  private final OffersPayloadMapper offersPayloadMapper =
      new OffersPayloadMapper(); // TODO move to unified provider namespace

  /** TODO returned type must be the MashMap/DTO - not a String TODO add custom exception */
  public String extractOffers(String rawJsonPayload) {

    if (null == rawJsonPayload || rawJsonPayload.isEmpty()) {
      throw new RuntimeException("Empty body payload"); // todo test that
    }

    try {
      JsonNode node = mapper.readTree(rawJsonPayload);

      Iterator<JsonNode> list =
          node.get("STORE_KEY").get("searchResponse").get("postings").elements();

      String a = "";

    } catch (JsonProcessingException e) {
      // todo log whole raw payload?
      throw new RuntimeException(e);
    }

    return "";
  }
}
