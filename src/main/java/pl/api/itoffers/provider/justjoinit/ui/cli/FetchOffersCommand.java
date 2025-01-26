package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItProviderImporter;

@ShellComponent
@RequiredArgsConstructor
public class FetchOffersCommand {
  private final JustJoinItProviderImporter offersCollector;

  @ShellMethod(key = "fetch")
  public String fetchJustJoinIt(
      @ShellOption(value = "-t", defaultValue = "") String requestedTechnology) {
    offersCollector.importOffers(requestedTechnology);
    return "Fetched";
  }
}
