package pl.api.itoffers.provider.justjoinit.ui.cli;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class ImportJJITOffersFromFileCli {
    // todo get scrappingId from a client, or generate uuid and show to the client
    // todo check if I can create S3 container in docker compose
    // todo move bucketname also to envs - maybe also filename should be in the env?

    @ShellMethod(key="jjit-s3")
    public void saveInMongoDBJJITOffersStoredInS3(
        @ShellOption(defaultValue = "test") String mode,
        @ShellOption(defaultValue = "1") String limit
    ) {

    }
}
