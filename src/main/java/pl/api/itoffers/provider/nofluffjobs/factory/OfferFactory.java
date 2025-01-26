package pl.api.itoffers.provider.nofluffjobs.factory;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.OfferMetadata;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.Origin;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;

public final class OfferFactory {

  private OfferFactory() {}

  public static Origin createOrigin(NoFluffJobsRawListOffer listOffer) {
    return new Origin(
        listOffer.getId().toString(), listOffer.getScrapingId(), Origin.Provider.NO_FLUFF_JOBS);
  }

  public static OfferMetadata createOfferMetadata(
      NoFluffJobsRawListOffer listOffer, NoFluffJobsRawDetailsOffer detailsOffer) {
    return new OfferMetadata(
        listOffer.getTechnology(),
        (String) listOffer.getOffer().get("url"),
        (String) listOffer.getOffer().get("title"),
        ((String) ((List) listOffer.getOffer().get("seniority")).get(0))
            .toLowerCase(Locale.getDefault()),
        (Boolean) ((Map) listOffer.getOffer().get("location")).get("fullyRemote")
            ? "remote"
            : "hybrid",
        (String) detailsOffer.getOffer().get("employmentType"),
        (Boolean) listOffer.getOffer().get("onlineInterviewAvailable"),
        Instant.ofEpochMilli(((Long) listOffer.getOffer().get("posted")).longValue())
            .atZone(ZoneId.of("Europe/Warsaw"))
            .toLocalDateTime());
  }

  public static Company createCompany(NoFluffJobsRawListOffer listOffer) {

    var locations = (List<Map>) ((Map) listOffer.getOffer().get("location")).get("places");
    var companyName = (String) listOffer.getOffer().get("name");
    var filtered =
        locations.stream()
            .filter(
                location ->
                    null == location.get("provinceOnly") && !"Remote".equals(location.get("city")))
            .toList();

    return filtered.isEmpty()
        ? Company.createWithNoAddress(companyName)
        : new Company(
            companyName,
            (String) filtered.get(0).get("city"),
            (String) filtered.get(0).get("street"));
  }

  public static Set<Category> createCategories(NoFluffJobsRawDetailsOffer detailsOffer) {
    return ((List<Map>) detailsOffer.getOffer().get("skills"))
        .stream()
            .map(skill -> new Category((String) skill.get("value")))
            .collect(Collectors.toSet());
  }

  public static Set<Salary> createSalaries(NoFluffJobsRawListOffer listOffer) {
    var salary = ((Map) listOffer.getOffer().get("salary"));

    return new HashSet<>(
        Set.of(
            Salary.original(
                (Integer) salary.get("from"),
                (Integer) salary.get("to"),
                (String) salary.get("currency"),
                (String) salary.get("type"))));
  }
}
