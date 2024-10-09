package pl.api.itoffers.offer.ui.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.*;

/**
 * OneUse - not finished!
 * @deprecated it's not finished - that solution with migrating Salaries it's a dead end because there is
 * a problem with matching RawOffers from MongoDB and with that Offers from Postgres. For one Postgres Offer
 * there can be multiple RawOffers, and sometimes you just fetch for incorrect MongoDB documents for current Postgres record.
 */
@Slf4j
@ShellComponent
public class MigrateOfferSalariesToNewFormatCli {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private JustJoinItRepository justJoinItRepository;

    //@ShellMethod(key="migrate-offer-salaries-to-new-format")
    @ShellMethod(key="m")
    public String migrate(String mode, int limit) {

        List<Offer> offers = offerRepository.findAll();

        int migrated = 0;
        int i = 0;
        for (Offer offer : offers) {
            if (!offer.getSalaries().isEmpty()) {
                continue;
            }
            i++;
            if (i >= limit) {
                break;
            }

            List<JustJoinItRawOffer> rawOffers = fetchRawOffers(offer, "Z");

            if (rawOffers.isEmpty()) {
                rawOffers = fetchRawOffers(offer, ".000Z");
                if (rawOffers.isEmpty()) {
                    log.error("Slug: \"{}\" \nTile: \"{}\" \nPublishedAt: \"{}\"\n", offer.getSlug(), offer.getTitle(), offer.getPublishedAt());
                }
            }

            for (JustJoinItRawOffer rawOffer : rawOffers) {
                Map<String, Object> first = rawOffers.get(0).getOffer();
                Map<String, Object> second = rawOffer.getOffer();

                ArrayList<LinkedHashMap> employmentTypes1 = (ArrayList<LinkedHashMap>) first.get("employmentTypes");
                ArrayList<LinkedHashMap> employmentTypes2 = (ArrayList<LinkedHashMap>) second.get("employmentTypes");

                Integer employmentTypes1To = (Integer) employmentTypes1.get(0).get("to");
                Integer employmentTypes1From = (Integer) employmentTypes1.get(0).get("from");
                String employmentTypes1Currency = (String) employmentTypes1.get(0).get("currency");
                String employmentTypes1Type = (String) employmentTypes1.get(0).get("type");

                Integer employmentTypes2To = (Integer) employmentTypes2.get(0).get("to");
                Integer employmentTypes2From = (Integer) employmentTypes2.get(0).get("from");
                String employmentTypes2Currency = (String) employmentTypes2.get(0).get("currency");
                String employmentTypes2Type = (String) employmentTypes2.get(0).get("type");

                if (null == employmentTypes1To && null == employmentTypes2To) {
                    continue;
                }

                if (!employmentTypes1To.equals(employmentTypes2To) || !employmentTypes1From.equals(employmentTypes2From))
                    log.warn("{} Different salary: {}-{}->{}-{}", offer.getId(), employmentTypes1From, employmentTypes1To, employmentTypes2From, employmentTypes2To);
                if (!employmentTypes1Currency.equals(employmentTypes2Currency))
                    log.warn("{} Different currency", offer.getId());
                if (!employmentTypes1Type.equals(employmentTypes2Type))
                    log.warn("{} Different employmentType", offer.getId());
            }

            if (false == mode.equals("migrate")) {
                continue;
            }
//
//            ArrayList<LinkedHashMap> employmentTypes= (ArrayList<LinkedHashMap>) rawOffers.get(0).getOffer().get("employmentTypes");
//
//            if (employmentTypes.isEmpty()) {
//                log.info(String.format("no salaries found in %s", offer.getId()));
//                continue;
//            }
//
//            for (LinkedHashMap employmentType : employmentTypes) {/*TODO it's duplicated in SalariesFactory*/
//                Integer to = (Integer) employmentType.get("to");
//                Integer from = (Integer) employmentType.get("from");
//                String currency = (String) employmentType.get("currency");
//                Boolean isPln = currency.equalsIgnoreCase("pln");
//
//                if (null == to || null == from) {
//                    continue;
//                }
//
//                salaryRepository.save(
//                    new Salary(
//                        offer,
//                        new SalaryAmount(from, to, currency.toUpperCase()),
//                        (String) employmentType.get("type"),
//                        true
//                    )
//                );
//
//                if (!isPln) {
//                    Double plnTo = (Double)employmentType.get("to_pln");
//                    Double plnFrom = (Double)employmentType.get("from_pln");
//
//                    if (null != plnTo && null != plnFrom) {
//                        salaryRepository.save(
//                            new Salary(
//                                offer,
//                                new SalaryAmount(plnFrom.intValue(), plnTo.intValue(), "PLN"),
//                                (String) employmentType.get("type"),
//                                false
//                            )
//                        );
//                    }
//                }
//                break; // salary is saved for this offer, so we can move to another offer
//            }
            migrated++;
        }

        log.info("Processed offers: {}", i);
        log.info("Migrated offers: {}", migrated);
        return "Migrated";
    }

    private List<JustJoinItRawOffer> fetchRawOffers(Offer offer, String publishedAtPostfix) {
        return justJoinItRepository.findDuplicatedOffers(
            offer.getTitle(),
            offer.getSlug(),
            offer.getCompany().getName(),
            offer.getPublishedAt()+publishedAtPostfix
        );
    }
}
