package pl.api.itoffers.provider.justjoinit.ui.cli;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import pl.api.itoffers.offer.application.TechnologyRepository;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ShellComponent
public class FetchOffersCommand {

    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private JustJoinItProvider justJoinItProvider;
    Logger logger = LoggerFactory.getLogger(FetchOffersCommand.class);

    @ShellMethod(key="fetch")
    public String fetchJustJoinIt(
            @ShellOption(value = "-t", defaultValue = "") String requestedTechnology
    ) {
        List<String> technologies = requestedTechnology.isEmpty()
            ? technologyRepository.allActive()
            : Arrays.asList(requestedTechnology);

        UUID scrapingId = UUID.randomUUID();

        for (String technology : technologies) {
            logger.info(String.format("[just-join-it] fetching offers from technology: %s", technology));
            justJoinItProvider.fetch(technology, scrapingId);
        }

        return "Fetched";
    }
}
