package pl.api.itoffers.data.nfj;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bson.types.ObjectId;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.shared.utils.fileManager.FileManager;
import pl.api.itoffers.shared.utils.json.JsonNodeMapper;

public class NoFluffJobsRawOfferModelsFactory {

  private static final JsonNodeMapper mapper = new JsonNodeMapper();

  public record NoFluffJobsRawModels(
      NoFluffJobsRawListOffer list, NoFluffJobsRawDetailsOffer details) {}

  public static NoFluffJobsRawModels create(NoFluffJobsParams.JsonOffer jsonOffer)
      throws IOException {
    var scrapingId = UUID.fromString("f8d7f4d7-b463-4a1a-9446-d2e3eb4a3c3a");
    var offerId = UUID.fromString("2a253bea-c3c7-4873-ab55-aa197b0d8670");

    var list =
        new NoFluffJobsRawListOffer(
            scrapingId,
            offerId,
            "php",
            mapper.mapToHash(FileManager.readFile(jsonOffer.list())),
            LocalDateTime.of(2025, 1, 11, 15, 30, 0));
    var details =
        new NoFluffJobsRawDetailsOffer(
            scrapingId,
            offerId,
            mapper.mapToHash(FileManager.readFile(jsonOffer.details())),
            LocalDateTime.of(2025, 1, 11, 15, 30, 2));

    setId(list, new ObjectId());
    setId(details, new ObjectId());

    return new NoFluffJobsRawModels(list, details);
  }

  private static void setId(Object mongoDbModel, ObjectId id) {
    try {
      var idField = mongoDbModel.getClass().getDeclaredField("id");
      idField.setAccessible(true);
      idField.set(mongoDbModel, id);
    } catch (Exception e) {
      throw new RuntimeException("Could not construct testing data");
    }
  }

  public static NoFluffJobsRawListOffer createOfferList(UUID offerId) {
    var offer =
        new NoFluffJobsRawListOffer(
            UUID.randomUUID(), offerId, "java", new HashMap<>(), LocalDateTime.now());

    return offer;
  }

  public static NoFluffJobsRawDetailsOffer createOfferDetails(UUID offerId) {
    var offer =
        new NoFluffJobsRawDetailsOffer(
            UUID.randomUUID(), offerId, new HashMap<>(), LocalDateTime.now());

    return offer;
  }

  public static NoFluffJobsRawDetailsOffer createDetailsOfferWith(List<String> categories) {
    var nfjOffer = new HashMap<String, Object>();
    var skills = new ArrayList<>();
    categories.forEach(
        categoryName -> {
          var skill = new HashMap<>();
          skill.put("value", categoryName);
          skills.add(skill);
        });
    nfjOffer.put("skills", skills);

    var offer =
        new NoFluffJobsRawDetailsOffer(
            UUID.randomUUID(), UUID.randomUUID(), nfjOffer, LocalDateTime.now());

    return offer;
  }
}
