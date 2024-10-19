package pl.api.itoffers.offer.ui.cli;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.offer.application.factory.SalariesFactory;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.*;

/**
 * OneUse - remove after usage
 * @deprecated
 */
@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class MigrateOfferSalariesToNewFormatCli {

    private final OfferRepository offerRepository;
    private final JustJoinItRepository justJoinItRepository;
    private final SalariesFactory salariesFactory;
    private final EntityManager entityManager;

    @ShellMethod(key="m")
    public String migrate(String mode, int limit) {

        log.info("Postgres All Offers - Start Fetching");
        List<Offer> offers = offerRepository.findAll();
        log.info("Postgres All Offers - Finished Fetching");

        int migrated = 0;
        int emptySalaries = 0;
        int i = 0;
        for (Offer offer : offers) {
            if (!offer.getSalaries().isEmpty()) {
                continue;
            }
            if (++i >= limit) {
                break;
            }

            List<JustJoinItRawOffer> rawOffers = fetchRawOffers(offer, "Z");

            if (rawOffers.isEmpty()) {
                rawOffers = fetchRawOffers(offer, ".000Z");
                if (rawOffers.isEmpty()) {
                    log.error("\nSlug: \"{}\" \nTile: \"{}\" \nPublishedAt: \"{}\"", offer.getSlug(), offer.getTitle(), offer.getPublishedAt());
                    continue;
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

            Set<Salary> salaries = salariesFactory.create(rawOffers.get(0));

            if (salaries.isEmpty()) {
                log.warn("No salaries for Offer {}", offer.getId());
                emptySalaries++;
                continue;
            }

            offer.setSalaries(salariesFactory.create(rawOffers.get(0)));
            entityManager.persist(offer);
            entityManager.flush();
            migrated++;
            log.info("Offer {} migrated", offer.getId());
        }

        log.info("Processed offers: {}", i);
        log.info("Migrated offers: {}", migrated);
        log.info("Empty salaries: {}", emptySalaries);
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
