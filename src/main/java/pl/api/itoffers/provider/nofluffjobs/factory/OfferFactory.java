package pl.api.itoffers.provider.nofluffjobs.factory;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@RequiredArgsConstructor
public class OfferFactory {

  private final ClockInterface clock;

  /** todo refactor it when functionality it's done */
  public Offer createOffer(
      NoFluffJobsRawListOffer listOffer,
      NoFluffJobsRawDetailsOffer detailsOffer,
      Set<Salary> salaries,
      Set<Category> categories) {
    return new Offer(
        new Origin(
            listOffer.getId().toString(), listOffer.getScrapingId(), Origin.Provider.NO_FLUFF_JOBS),
        listOffer.getTechnology(),
        (String) listOffer.getOffer().get("url"),
        (String) listOffer.getOffer().get("title"),
        ((String) ((List) listOffer.getOffer().get("seniority")).get(0))
            .toLowerCase(Locale.getDefault()),
        new Characteristics(
            (Boolean)
                    ((Map) listOffer.getOffer().get("location"))
                        .get("fullyRemote") /*todo test both paths*/
                ? "remote"
                : "hybrid",
            (String) detailsOffer.getOffer().get("employmentType"),
            (Boolean) listOffer.getOffer().get("onlineInterviewAvailable")),
        categories,
        salaries,
        createCompany(listOffer),
        Instant.ofEpochMilli(
                Long.parseLong(
                    (String)
                        ((LinkedHashMap) listOffer.getOffer().get("posted")).get("$numberLong")))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        clock.now());
  }

  private static Company createCompany(NoFluffJobsRawListOffer listOffer) {

    var locations = (List<Map>) ((Map) listOffer.getOffer().get("location")).get("places");
    var filtered =
        locations.stream()
            .filter(
                location ->
                    null == location.get("provinceOnly") && !"Remote".equals(location.get("city")))
            .toList();

    if (filtered.size() != 1) {
      throw NoFluffJobsException.onMappingToDomainModel(
          "company", listOffer.toString()); // todo test that path
    }

    return new Company(
        (String) listOffer.getOffer().get("name"),
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
