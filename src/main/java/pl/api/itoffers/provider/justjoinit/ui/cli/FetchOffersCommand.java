package pl.api.itoffers.provider.justjoinit.ui.cli;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.service.FetchJustJoinItOffersService;

@ShellComponent
@RequiredArgsConstructor
public class FetchOffersCommand {
  private final FetchJustJoinItOffersService fetchJustJoinItOffersService;

  @ShellMethod(key = "fetch")
  public String fetchJustJoinIt(
      @ShellOption(value = "-t", defaultValue = "") String requestedTechnology) {
    fetchJustJoinItOffersService.fetch(requestedTechnology);
    return "Fetched";
  }
}
