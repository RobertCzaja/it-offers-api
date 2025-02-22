package pl.api.itoffers.provider.justjoinit.ui.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.shared.aws.AwsS3Connector;
import pl.api.itoffers.shared.utils.cli.CliFixParams;
import pl.api.itoffers.shared.utils.cli.FixReport;
import pl.api.itoffers.shared.utils.json.JsonNodeMapper;

/** OneUse */
@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class ImportJJITOffersFromFileCli {
  private static final String FILE_NAME = "dev-recruit.just_join_it_raw_offers.json";

  private AwsS3Connector awsS3Connector;
  private ObjectMapper mapper;
  private JustJoinItRepository repository;

  @ShellMethod(key = "jjit-s3")
  public void saveInMongoDBJJITOffersStoredInS3(
      @ShellOption(defaultValue = "test") String mode,
      @ShellOption(defaultValue = "1") String limit)
      throws IOException {

    CliFixParams params = new CliFixParams(mode);

    log.info("Start fetching {}", FILE_NAME);
    String justJoinItOffers;
    try {
      justJoinItOffers = awsS3Connector.fetchJson(FILE_NAME);
    } catch (Exception e) {
      log.error("{}", e);
      e.printStackTrace();
      throw e;
    }

    log.info("Fetched");
    ArrayList<Map<String, Object>> rawOffers =
        new JsonNodeMapper().mapToList(mapper.readTree(justJoinItOffers).elements());
    UUID scrappingId = UUID.randomUUID();
    log.info("ScrappingId: {}", scrappingId);

    Map<String, List<JustJoinItRawOffer>> jjitOffers = new HashMap<>();

    for (Map<String, Object> rawOffer : rawOffers) {
      Map<String, Object> offerMetadata = (Map<String, Object>) rawOffer.get("offer");
      String slug = (String) offerMetadata.get("slug");
      List<JustJoinItRawOffer> alreadySavedSameOffers = jjitOffers.get(slug);

      if (null == alreadySavedSameOffers) {
        List<JustJoinItRawOffer> jjitOffersCollection = new ArrayList<>();
        jjitOffersCollection.add(createJJITDocument(scrappingId, rawOffer));
        jjitOffers.put(slug, jjitOffersCollection);
      } else {
        alreadySavedSameOffers.add(createJJITDocument(scrappingId, rawOffer));
      }
    }

    FixReport report = new FixReport(Integer.valueOf(limit), jjitOffers.size());

    if (!params.isMigration()) {
      log.info("{}all offers in s3 file: {}\n", report, rawOffers.size());
      return;
    }

    for (Map.Entry<String, List<JustJoinItRawOffer>> groupedOffers : jjitOffers.entrySet()) {
      List<JustJoinItRawOffer> currentOffers = groupedOffers.getValue();
      JustJoinItRawOffer currentOffer = currentOffers.get(0);

      List<JustJoinItRawOffer> alreadyStored =
          repository.findOriginatedRawOffers(
              (String) currentOffer.getOffer().get("title"),
              (String) currentOffer.getOffer().get("slug"),
              (String) currentOffer.getOffer().get("companyName"),
              (String) currentOffer.getOffer().get("experienceLevel"));

      if (!alreadyStored.isEmpty()) {
        log.warn("[already-stored] {}", currentOffer.getOffer().get("slug"));
        continue;
      }

      report.startProcessing();
      if (report.limitReached()) {
        break;
      }

      log.info("[new-offer] {}", currentOffer.getOffer().get("slug"));

      repository.save(getNewest(currentOffers));

      report.migratedSuccessfully();
    }

    log.info("{}all offers in s3 file: {}\n", report, rawOffers.size());
  }

  private static JustJoinItRawOffer getNewest(List<JustJoinItRawOffer> offers) {
    JustJoinItRawOffer newest = offers.get(0);

    for (JustJoinItRawOffer currentOffer : offers) {
      LocalDateTime currentPublishedAt =
          JustJoinItDateTime.createFrom((String) currentOffer.getOffer().get("publishedAt")).value;
      LocalDateTime newestPublishedAt =
          JustJoinItDateTime.createFrom((String) newest.getOffer().get("publishedAt")).value;

      if (newestPublishedAt.isBefore(currentPublishedAt)) {
        newest = currentOffer;
      }
    }

    return newest;
  }

  public static JustJoinItRawOffer createJJITDocument(
      UUID scrappingId, Map<String, Object> rawOffer) {
    LinkedHashMap<String, String> rawCreatedAt =
        (LinkedHashMap<String, String>) rawOffer.get("createdAt");
    Map<String, Object> offerMetadata = (Map<String, Object>) rawOffer.get("offer");

    return new JustJoinItRawOffer(
        scrappingId,
        (String) rawOffer.get("technology"),
        offerMetadata,
        JustJoinItDateTime.createFrom(rawCreatedAt.get("$date")).value);
  }
}
