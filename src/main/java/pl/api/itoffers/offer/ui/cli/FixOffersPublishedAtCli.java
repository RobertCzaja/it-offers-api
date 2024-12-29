package pl.api.itoffers.offer.ui.cli;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.shared.utils.cli.CliFixParams;
import pl.api.itoffers.shared.utils.cli.FixReport;

/**
 * OneUse - should be used only once, only for data fix
 *
 * @deprecated
 */
@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class FixOffersPublishedAtCli {
  private static final LocalDateTime DATETIME_TO_FIX =
      LocalDateTime.parse("2024-07-27T15:00:38.890000");

  private final OfferRepository offerRepository;
  private final JustJoinItRepository justJoinItRepository;
  private final EntityManager em;

  @ShellMethod(key = "i")
  public void fixAllOffersWithWrongPublishedAt(
      @ShellOption(defaultValue = "test") String mode,
      @ShellOption(defaultValue = "1") String limit) {
    CliFixParams params = new CliFixParams(mode);
    log.info("Input: {}", params);

    List<Offer> offers = offerRepository.findByPublishedAt(DATETIME_TO_FIX);
    log.info("Found: {}", offers.size());
    FixReport report = new FixReport(Integer.valueOf(limit), offers.size());

    for (Offer offer : offers) {
      report.startProcessing();
      if (report.limitReached()) {
        break;
      }

      List<JustJoinItRawOffer> justJoinItRawOffers = fetchRawOffers(offer);

      if (justJoinItRawOffers.isEmpty()) {
        log.error(
            "No related offer in MongoDB: \nSlug: \"{}\" \nTile: \"{}\"",
            offer.getSlug(),
            offer.getTitle());
        continue;
      }

      try {
        areRawOffersAreTheSameOriginOffer(justJoinItRawOffers, params.isForceMode());
      } catch (RuntimeException e) {
        log.warn("[Offer: {}] Different {}", offer.getId(), e.getMessage());
        continue;
      }

      LocalDateTime correctPublishedAt = getTheOldestPublishedAt(justJoinItRawOffers);
      log.info("[Offer: {}] {}->{}", offer.getId(), offer.getPublishedAt(), correctPublishedAt);

      if (!params.isMigration()) {
        continue;
      }

      offer.setPublishedAt(correctPublishedAt);

      em.persist(offer);
      em.flush();

      report.migratedSuccessfully();
      log.info("Offer {} migrated", offer.getId());
    }

    log.info("{}mode: {} \n", report, mode);
  }

  public static LocalDateTime getTheOldestPublishedAt(List<JustJoinItRawOffer> rawOffers) {
    LocalDateTime theOldestPublishedAt =
        JustJoinItDateTime.createFrom((String) rawOffers.get(0).getOffer().get("publishedAt"))
            .value;

    for (JustJoinItRawOffer rawOffer : rawOffers) {
      LocalDateTime publishedAt =
          JustJoinItDateTime.createFrom((String) rawOffer.getOffer().get("publishedAt")).value;

      if (publishedAt.isBefore(theOldestPublishedAt)) {
        theOldestPublishedAt = publishedAt;
      }
    }

    return theOldestPublishedAt;
  }

  private static void areRawOffersAreTheSameOriginOffer(
      List<JustJoinItRawOffer> rawOffers, boolean noValidation) {
    if (noValidation) {
      return;
    }
    for (JustJoinItRawOffer rawOffer : rawOffers) {
      Map<String, Object> first = rawOffers.get(0).getOffer();
      Map<String, Object> second = rawOffer.getOffer();

      ArrayList<String> requiredSkills1 = (ArrayList<String>) first.get("requiredSkills");
      ArrayList<String> requiredSkills2 = (ArrayList<String>) second.get("requiredSkills");

      Collections.sort(requiredSkills1);
      Collections.sort(requiredSkills2);

      if (!requiredSkills2.equals(requiredSkills1)) {
        throw new RuntimeException(
            String.format("requiredSkills: %s->%s", requiredSkills1, requiredSkills2));
      }
    }
  }

  private List<JustJoinItRawOffer> fetchRawOffers(Offer offer) {
    return justJoinItRepository.findOriginatedRawOffers(
        offer.getTitle(), offer.getSlug(), offer.getCompany().getName(), offer.getSeniority());
  }
}
