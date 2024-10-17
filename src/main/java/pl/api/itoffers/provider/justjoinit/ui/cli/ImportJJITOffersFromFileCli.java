package pl.api.itoffers.provider.justjoinit.ui.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;
import pl.api.itoffers.shared.aws.AwsS3Connector;

import java.io.IOException;
import java.util.*;

@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class ImportJJITOffersFromFileCli {
    // todo check if I can create S3 container in docker compose
    // todo create next cli to migrate JJIT MongoDB Offers to Postgres Offer
    // todo once it will be functionally finished - refactor all/create separated services to fetch JSON file
    private static final String FILE_NAME = "dev-recruit.just_join_it_raw_offers.json";

    private AwsS3Connector awsS3Connector;
    private ObjectMapper mapper;
    private JustJoinItRepository repository;
    private JustJoinItPayloadExtractor extractor;

    @ShellMethod(key="jjit-s3")
    public void saveInMongoDBJJITOffersStoredInS3(
        @ShellOption(defaultValue = "test") String mode,
        @ShellOption(defaultValue = "1") String limit
    ) throws IOException {

        log.info("Start fetching {}", FILE_NAME);
        String justJoinItOffers = awsS3Connector.fetchJson(FILE_NAME);
        log.info("Fetched");
        ArrayList<Map<String, Object>> offers = extractor.convert(mapper.readTree(justJoinItOffers).elements());
        UUID scrappingId = UUID.randomUUID();
        log.info("ScrappingId: {}", scrappingId);

        // todo add limit
        // todo should be grouped by slug first?
        // todo check in MongoDB it is not already added
        offers.forEach(offer -> {
            LinkedHashMap<String, String> rawCreatedAt = (LinkedHashMap<String, String>) offer.get("createdAt");

            String a = "";

//            repository.save(
//                new JustJoinItRawOffer(
//                    scrappingId,
//                    (String) offer.get("technology"),
//                    offer,
//                    JustJoinItDateTime.createFrom(rawCreatedAt.get("$date")).value
//                )
//            );
        });
    }
}
