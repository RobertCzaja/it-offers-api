package pl.api.itoffers.offer.ui.cli;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.application.repository.SalaryRepository;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.offer.domain.SalaryAmount;
import pl.api.itoffers.offer.domain.SalaryId;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import software.amazon.awssdk.services.secretsmanager.endpoints.internal.Value;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * OneUse
 */
@Slf4j
@ShellComponent
public class MigrateOfferSalariesToNewFormatCli {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private SalaryRepository salaryRepository;
    @Autowired
    private JustJoinItRepository justJoinItRepository;

    /*todo change name to: migrate-offer-salaries-to-new-format*/
    @ShellMethod(key="m")
    public String migrate(String mode) {

        List<Offer> offers = offerRepository.findAll();

        for (Offer offer : offers) {
            log.info(String.format("offer %s", offer.getId()));
            List<JustJoinItRawOffer> rawOffers = justJoinItRepository.findDuplicatedOffers(
                offer.getTitle(),
                offer.getSlug(),
                offer.getCompany().getName()
            );

            if (rawOffers.isEmpty()) {
                throw new RuntimeException(String.format("Raw data does not exist for OfferId %s", offer.getId()));
            }

            // todo check if all offers from collection are the same

            if (false == mode.equals("migrate")) {
                continue;
            }

            ArrayList<LinkedHashMap> employmentTypes= (ArrayList<LinkedHashMap>) rawOffers.get(0).getOffer().get("employmentTypes");

            if (employmentTypes.isEmpty()) {
                log.info(String.format("no salaries found in %s", offer.getId()));
                continue;
            }

            for (LinkedHashMap employmentType : employmentTypes) {
                Integer to = (Integer) employmentType.get("to");
                Integer from = (Integer) employmentType.get("from");
                String currency = (String) employmentType.get("currency");
                Boolean isPln = currency.equalsIgnoreCase("pln");

                if (null == to || null == from) {
                    continue;
                }

                salaryRepository.save(
                    new Salary(
                        new SalaryId(offer.getId(), currency.toUpperCase()),
                        new SalaryAmount(from, to),
                        (String) employmentType.get("type"),
                        true
                    )
                );

                if (!isPln) {
                    Double plnTo = (Double)employmentType.get("to_pln");
                    Double plnFrom = (Double)employmentType.get("from_pln");

                    if (null != plnTo && null != plnFrom) {
                        salaryRepository.save(
                            new Salary(
                                new SalaryId(offer.getId(), "PLN"),
                                new SalaryAmount(plnFrom.intValue(), plnTo.intValue()),
                                (String) employmentType.get("type"),
                                false
                            )
                        );
                    }
                }
                break; // salary is saved for this offer, so we can move to another offer
            }
        }

        return "Migrated";
    }

}
