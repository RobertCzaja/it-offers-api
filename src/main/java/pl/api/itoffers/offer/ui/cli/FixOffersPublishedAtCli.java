package pl.api.itoffers.offer.ui.cli;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * OneUse - should be used only once, only for data fix
 * @deprecated
 */
@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class FixOffersPublishedAtCli {
    /*TODO set here "2024-07-27T15:00:38.890000" when time to PROD fix has come*/
    private static final LocalDateTime DATETIME_TO_FIX = LocalDateTime.parse("2024-09-26T05:00:29.065000");

    private final OfferRepository offerRepository;
    private final JustJoinItRepository justJoinItRepository;

    @ShellMethod(key="i")
    public void fixAllOffersWithWrongPublishedAt(
        @ShellOption(defaultValue = "test") String mode,
        @ShellOption(defaultValue = "1") String limit
    ) {
        CliFixParams params = new CliFixParams(mode, Integer.valueOf(limit));
        log.info("Input: {}", params);

        List<Offer> offers = offerRepository.findByPublishedAt(DATETIME_TO_FIX);
        log.info("Found: {}", offers.size());

        for (Offer offer : offers) {
            // todo apply limit checking
            List<JustJoinItRawOffer> justJoinItRawOffers = fetchRawOffers(offer);

            if (justJoinItRawOffers.isEmpty()) {
                log.error("No related offer in MongoDB: \nSlug: \"{}\" \nTile: \"{}\"", offer.getSlug(), offer.getTitle());
                continue;
            }

            areRawOffersAreTheSameOriginOffer(justJoinItRawOffers);

            // todo: get first
            // todo: update publishedAt
            // todo: save

            if (params.isMigration()) {
                return;
            }
        }

        // todo add stats:
    }

    private static void areRawOffersAreTheSameOriginOffer(List<JustJoinItRawOffer> rawOffers) {
        // todo add implementation
    }

    private List<JustJoinItRawOffer> fetchRawOffers(Offer offer) {
        return justJoinItRepository.findOriginatedRawOffers(
            offer.getTitle(),
            offer.getSlug(),
            offer.getCompany().getName(),
            offer.getSeniority()
        );
    }
}
