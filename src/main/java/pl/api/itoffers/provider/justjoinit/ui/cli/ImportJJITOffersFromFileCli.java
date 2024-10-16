package pl.api.itoffers.provider.justjoinit.ui.cli;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.shared.aws.AwsS3Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class ImportJJITOffersFromFileCli {
    // todo get scrappingId from a client, or generate uuid and show to the client
    // todo check if I can create S3 container in docker compose
    // todo create next cli to migrate JJIT MongoDB Offers to Postgres Offer
    private static final String FILE_NAME = "dev-recruit.just_join_it_raw_offers.json";

    private AwsS3Connector awsS3Connector;

    @ShellMethod(key="jjit-s3")
    public void saveInMongoDBJJITOffersStoredInS3(
        @ShellOption(defaultValue = "test") String mode,
        @ShellOption(defaultValue = "1") String limit
    ) throws IOException {

        // todo add log that fetching file just started
        String justJoinItOffers = fetchJson();
        // todo add log that fetching file has been fetched
        // todo convert that string to JSON object

        // todo add limit
        // todo check if JJIT raw offer isn't already in MongoDB
    }

    // todo move to some service
    private String fetchJson() throws IOException {
        S3ObjectInputStream inputStream = awsS3Connector.download(FILE_NAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
