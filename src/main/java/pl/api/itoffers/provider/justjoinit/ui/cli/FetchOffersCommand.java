package pl.api.itoffers.provider.justjoinit.ui.cli;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.offer.application.repository.TechnologyRepository;
import pl.api.itoffers.offer.application.service.OfferService;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;
import pl.api.itoffers.provider.justjoinit.service.FetchJustJoinItOffersService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
