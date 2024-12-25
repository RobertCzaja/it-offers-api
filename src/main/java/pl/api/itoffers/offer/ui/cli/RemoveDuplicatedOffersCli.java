package pl.api.itoffers.offer.ui.cli;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.shared.utils.cli.CliFixParams;
import pl.api.itoffers.shared.utils.cli.FixReport;

import java.util.*;

/**
 * OneUse - remove after usage
 * @deprecated
 */
@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class RemoveDuplicatedOffersCli {

    private final OfferRepository offerRepository;

    @ShellMethod(key="r")
    public void removeDuplications(
        @ShellOption(defaultValue = "test") String mode,
        @ShellOption(defaultValue = "1") String limit
    ) {
        CliFixParams params = new CliFixParams(mode);

        log.info("Postgres All Offers - Start Fetching");
        List<Offer> offers = offerRepository.findAllByOrderBySlugAsc();
        log.info("Found: {}", offers.size());
        FixReport report = new FixReport(Integer.valueOf(limit), offers.size());

        HashMap<String, ArrayList<Offer>> groupedSameOffers = new HashMap<>();
        for (Offer offer : offers) {
            ArrayList<Offer> alreadyAddedOffersGroup = groupedSameOffers.get(offer.getSlug());

            if (null == alreadyAddedOffersGroup) {
                ArrayList<Offer> groupedOffers = new ArrayList<>();
                groupedOffers.add(offer);
                groupedSameOffers.put(offer.getSlug(), groupedOffers);
            } else {
                alreadyAddedOffersGroup.add(offer);
            }
        }

        groupedSameOffers.forEach((slug, groupedOffers) -> {
            groupedOffers.sort(new Comparator<Offer>() {
                @Override
                public int compare(Offer o1, Offer o2) {
                    return o1.getPublishedAt().compareTo(o2.getPublishedAt());
                }
            });
        });

        int duplicatedOffersCount = 0;
        for (Map.Entry<String, ArrayList<Offer>> groupedOffers : groupedSameOffers.entrySet()) {
            if (groupedOffers.getValue().size() > 1) {
                duplicatedOffersCount++;
            }
        }

        for (Map.Entry<String, ArrayList<Offer>> groupedOffers : groupedSameOffers.entrySet()) {}

        if (params.isMigration()) {
            for (Map.Entry<String, ArrayList<Offer>> groupedOffers : groupedSameOffers.entrySet()) {
                report.startProcessing();
                if (report.limitReached()) {
                    break;
                }

                ArrayList<Offer> currentOffers = groupedOffers.getValue();

                if (currentOffers.size() > 1) {
                    currentOffers.remove(currentOffers.size() - 1);
                    offerRepository.deleteAll(currentOffers);
                    report.migratedSuccessfully();
                }
            }
        }

        log.info("{}mode: {} \nduplicated offers: {}\n", report, mode, duplicatedOffersCount);
    }
}
