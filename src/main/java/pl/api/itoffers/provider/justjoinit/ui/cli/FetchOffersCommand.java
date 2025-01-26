package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.factory.JustJoinItProviderImporterFactory;

@ShellComponent
@RequiredArgsConstructor
public class FetchOffersCommand {
  private final JustJoinItProviderImporterFactory importerFactory;

  @ShellMethod(key = "fetch")
  public String fetchJustJoinIt(
      @ShellOption(value = "-t", defaultValue = "") String requestedTechnology) {
    importerFactory.create().importOffers(requestedTechnology);
    return "Fetched";
  }
}
