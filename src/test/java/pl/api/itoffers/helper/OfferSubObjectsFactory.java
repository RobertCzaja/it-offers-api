package pl.api.itoffers.helper;

import java.util.Set;
import java.util.UUID;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.Origin;

public class OfferSubObjectsFactory {
  public static Origin createOrigin(String id) {
    return new Origin(id, UUID.randomUUID(), Origin.Provider.NO_FLUFF_JOBS);
  }

  public static Set<Salary> createSalaries() {
    return Set.of(Salary.original(12000, 14000, "EUR", "b2b"));
  }

  public static Company createCompany() {
    return new Company("a", "a", "a");
  }
}
