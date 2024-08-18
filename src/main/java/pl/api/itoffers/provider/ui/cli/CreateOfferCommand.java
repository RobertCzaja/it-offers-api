package pl.api.itoffers.provider.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.provider.Offer;
import pl.api.itoffers.provider.infrastructure.OfferRepository;

@ShellComponent
public class CreateOfferCommand {

    @Autowired
    private OfferRepository repository;

    @ShellMethod(key="offer")
    public String create() {
        Offer offer = new Offer("3", "test");
        repository.save(offer);
        return "Created";
    }
}
