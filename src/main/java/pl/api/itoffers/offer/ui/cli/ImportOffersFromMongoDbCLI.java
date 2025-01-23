package pl.api.itoffers.offer.ui.cli;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.offer.application.service.OfferService;

@Slf4j
@ShellComponent
@Transactional
@AllArgsConstructor
public class ImportOffersFromMongoDbCLI {

  private final OfferService offerService;

  @ShellMethod(key = "mongodb-import")
  public void importJJITMongoDBOfferToPostgres(
      @ShellOption(defaultValue = "") String rawScrappingId,
      @ShellOption(defaultValue = "test") String mode,
      @ShellOption(defaultValue = "1") String limit) {
    offerService.processOffersFromExternalService(UUID.fromString(rawScrappingId));
  }
}
