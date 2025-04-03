package pl.api.itoffers.provider.justjoinit.service.v2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.exception.JustJoinItException;
import pl.api.itoffers.shared.utils.json.JsonNodeMapper;

@Slf4j
@Service
public class PayloadFromJsonExtractor {
  private static final String KEY_STATE = "state";

  private final ObjectMapper mapper = new ObjectMapper();
  private final JsonNodeMapper payloadMapper = new JsonNodeMapper();

  public ArrayList<Map<String, Object>> extractPayload(String rawJsonPayload) {
    if (null == rawJsonPayload || rawJsonPayload.isEmpty()) {
      throw new JustJoinItException("Empty body payload");
    }

    try {
      Iterator<JsonNode> queriesNodes =
          mapper.readTree(rawJsonPayload).path(KEY_STATE).path("queries").elements();

      if (!queriesNodes.hasNext()) {
        log.info("Empty JJIT payload: \n{}", rawJsonPayload);
        return new ArrayList<>();
      }

      do {
        JsonNode queryNode = queriesNodes.next();

        try {
          return payloadMapper.mapToList(
              queryNode.get(KEY_STATE).path("data").path("pages").get(0).path("data").elements());
        } catch (NullPointerException e) {
          /*do nothing, allows to move to the next iteration*/
        }
      } while (queriesNodes.hasNext());

      throw new JustJoinItException("Could not find offers node in raw JSON payload.");
    } catch (JsonProcessingException e) {
      throw new JustJoinItException(
          "Could not extract offers from raw JSON payload. " + e.getMessage(), e);
    } catch (NoSuchElementException e) {
      log.error("{}", rawJsonPayload);
      throw e;
    }
  }
}
