package pl.api.itoffers.provider.justjoinit.ui.cli;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.provider.common.application.TechnologyRepository;
import pl.api.itoffers.provider.justjoinit.JustJoinItProvider;

import java.util.UUID;

@ShellComponent
public class FetchOffersCommand {

    @Autowired
    private TechnologyRepository technologyRepository;
    @Autowired
    private JustJoinItProvider justJoinItProvider;
    Logger logger = LoggerFactory.getLogger(FetchOffersCommand.class);

    @ShellMethod(key="fetch")
    public String fetchJustJoinIt() {

        UUID scrapingId = UUID.randomUUID();

        for (String technology : technologyRepository.allActive()) {
            logger.info(String.format("[just-join-it] fetching offers from technology: %s", technology));
            justJoinItProvider.fetch(technology, scrapingId);
        }

        return "Fetched";
    }
}
