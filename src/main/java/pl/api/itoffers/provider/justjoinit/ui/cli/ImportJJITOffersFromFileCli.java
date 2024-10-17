package pl.api.itoffers.provider.justjoinit.ui.cli;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItPayloadExtractor;
import pl.api.itoffers.shared.aws.AwsS3Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class ImportJJITOffersFromFileCli {
    // todo get scrappingId from a client, or generate uuid and show to the client
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
        String justJoinItOffers = fetchJson(FILE_NAME);
        log.info("Fetched");
        ArrayList<Map<String, Object>> offers = extractor.convert(mapper.readTree(justJoinItOffers).elements());
        UUID scrappingId = UUID.randomUUID();
        log.info("ScrappingId: {}", scrappingId);

        // todo check in MongoDB it is not already added
        offers.forEach(offer -> {
            LinkedHashMap<String, String> rawCreatedAt = (LinkedHashMap<String, String>) offer.get("createdAt");

            String a = "";

            repository.save(
                new JustJoinItRawOffer(
                    scrappingId,
                    (String) offer.get("technology"),
                    offer,
                    JustJoinItDateTime.createFrom(rawCreatedAt.get("$date")).value
                )
            );
        });

        // todo should be grouped by slug first?
        // todo add limit
        // todo check if JJIT raw offer isn't already in MongoDB
    }

    // todo move to some service
    private String fetchJson(String fileName) throws IOException {
        S3ObjectInputStream inputStream = awsS3Connector.download(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
