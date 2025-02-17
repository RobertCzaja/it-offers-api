package pl.api.itoffers.provider.justjoinit.factory;

import java.util.*;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

@Service
public class SalariesFactory {

  public Set<Salary> create(JustJoinItRawOffer rawOffer) {
    var salaries = new HashSet<Salary>();
    var employmentTypes = (List<LinkedHashMap>) rawOffer.getOffer().get("employmentTypes");

    if (null == employmentTypes) {
      throw new RuntimeException("employmentTypes key does not exist");
    }

    employmentTypes.forEach(employmentType -> salaries.addAll(createSalaries(employmentType)));

    return salaries;
  }

  private Set<Salary> createSalaries(LinkedHashMap employmentType) {
    var salaries = new HashSet<Salary>();
    var to = (Integer) employmentType.get("to");
    var from = (Integer) employmentType.get("from");
    var currency = (String) employmentType.get("currency");
    var isPln = "pln".equalsIgnoreCase(currency);

    if (null == to || null == from) {
      return salaries;
    }

    salaries.add(Salary.original(from, to, currency, (String) employmentType.get("type")));

    if (!isPln) {
      var plnTo = toDouble(employmentType.get("to_pln"));
      var plnFrom = toDouble(employmentType.get("from_pln"));

      if (null != plnTo && null != plnFrom) {
        salaries.add(
            Salary.convertedToPLN(
                plnFrom.intValue(), plnTo.intValue(), (String) employmentType.get("type")));
      }
    }

    return salaries;
  }

  private static Double toDouble(Object amount) {
    if (null == amount) {
      return 0D;
    }

    return (amount instanceof String)
        ? Double.parseDouble((String) amount)
        : Double.valueOf(String.valueOf(amount));
  }
}
