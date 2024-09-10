package pl.api.itoffers.provider.justjoinit.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.provider.justjoinit.service.FetchJustJoinItOffersService;

@ShellComponent
public class FetchOffersCommand {
    @Autowired
    private FetchJustJoinItOffersService fetchJustJoinItOffersService;

    @ShellMethod(key="fetch")
    public String fetchJustJoinIt(
            @ShellOption(value = "-t", defaultValue = "") String requestedTechnology
    ) {
        fetchJustJoinItOffersService.fetch(requestedTechnology);
        return "Fetched";
    }
}
