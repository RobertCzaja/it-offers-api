package pl.api.itoffers.provider.justjoinit.ui.cli;

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

    @ShellMethod(key="fetch")
    public String fetchJustJoinIt() {

        UUID scrapingId = UUID.randomUUID();

        for (String technology : technologyRepository.allActive()) {
            // todo add logger
            justJoinItProvider.fetch(technology, scrapingId);
        }

        return "Fetched";
    }
}
