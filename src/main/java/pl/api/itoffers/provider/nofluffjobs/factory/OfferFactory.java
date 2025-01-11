package pl.api.itoffers.provider.nofluffjobs.factory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Characteristics;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Origin;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;

@RequiredArgsConstructor
public class OfferFactory {

  /** todo to implement */
  public Offer createOffer(
      NoFluffJobsRawListOffer listOffer, NoFluffJobsRawDetailsOffer detailsOffer) {
    return new Offer(
        new Origin(
            listOffer.getId().toString(), listOffer.getScrapingId(), Origin.Provider.NO_FLUFF_JOBS),
        listOffer.getTechnology(),
        (String) listOffer.getOffer().get("url"),
        (String) listOffer.getOffer().get("title"),
        "Senior", // todo "seniority"[0]; map to the same ones like in jjit[c_level, senior, mid,
        // junior]; nfj[]
        new Characteristics( // todo where get that values?; map to the same ones like in jjit
            "workplace", // todo jjit[remote,office,hybrid]; nfj[]
            "time", // todo jjit[Undetermined,full_time,freelance,internship,part_time]; nfj[]
            true // todo
            ),
        new HashSet<Category>(), // todo
        new HashSet<Salary>(), // todo
        new Company(
            "company name", // todo
            "Warsaw", // todo
            "Opolska 34/2" // todo
            ), // todo
        Instant.ofEpochMilli(
                Long.parseLong(
                    (String)
                        ((LinkedHashMap) listOffer.getOffer().get("posted")).get("$numberLong")))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(),
        LocalDateTime.now());
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
