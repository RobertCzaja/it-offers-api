package pl.api.itoffers.data.nfj;

import java.io.IOException;
import java.time.LocalDateTime;
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

  public static NoFluffJobsRawModels create()
      throws IOException, NoSuchFieldException, IllegalAccessException {
    var scrapingId = UUID.fromString("f8d7f4d7-b463-4a1a-9446-d2e3eb4a3c3a");
    var offerId = UUID.fromString("2a253bea-c3c7-4873-ab55-aa197b0d8670");

    var list =
        new NoFluffJobsRawListOffer(
            scrapingId,
            offerId,
            "php",
            mapper.mapToHash(FileManager.readFile(NoFluffJobsParams.A1_PHP_JSON_OFFER.LIST)),
            LocalDateTime.of(2025, 1, 11, 15, 30, 0));
    var details =
        new NoFluffJobsRawDetailsOffer(
            scrapingId,
            offerId,
            mapper.mapToHash(FileManager.readFile(NoFluffJobsParams.A1_PHP_JSON_OFFER.DETAILS)),
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
}
