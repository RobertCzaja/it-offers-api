package pl.api.itoffers.provider.nofluffjobs.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import lombok.RequiredArgsConstructor;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Characteristics;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.nofluffjobs.exception.NoFluffJobsException;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;

@RequiredArgsConstructor
public class OfferFactory {

  /** todo to implement; refactor it when functionality it's done */
  public Offer createOffer(
      NoFluffJobsRawListOffer listOffer, NoFluffJobsRawDetailsOffer detailsOffer) {
    Boolean isFullyRemote =
        (Boolean) ((Map) listOffer.getOffer().get("location")).get("fullyRemote");

    return new Offer(
        new Origin(
            listOffer.getId().toString(), listOffer.getScrapingId(), Origin.Provider.NO_FLUFF_JOBS),
        listOffer.getTechnology(),
        (String) listOffer.getOffer().get("url"),
        (String) listOffer.getOffer().get("title"),
        ((String) ((List) listOffer.getOffer().get("seniority")).get(0))
            .toLowerCase(Locale.getDefault()),
        new Characteristics(
            isFullyRemote ? "remote" : "hybrid" /*todo test both paths*/,
            (String) detailsOffer.getOffer().get("employmentType"),
            (Boolean) listOffer.getOffer().get("onlineInterviewAvailable")),
        new HashSet<Category>(), // todo
        new HashSet<Salary>(), // todo
        evaluateCompany(listOffer),
        Instant.ofEpochMilli(
                Long.parseLong(
                    (String)
                        ((LinkedHashMap) listOffer.getOffer().get("posted")).get("$numberLong")))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        LocalDateTime.now());
  }

  private static Company evaluateCompany(NoFluffJobsRawListOffer listOffer) {

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

  /** todo to implement */
  public Map<String, Set<Category>> createCategories() {
    return null;
  }

  /** todo to implement */
  public Company createCompany() {
    return null;
  }

  /** todo to implement */
  private Characteristics createCharacteristics() {
    return null;
  }
}
