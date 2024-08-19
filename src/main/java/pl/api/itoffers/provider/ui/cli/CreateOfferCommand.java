package pl.api.itoffers.provider.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.provider.Offer;
import pl.api.itoffers.provider.infrastructure.OfferRepository;

// TODO to remove, only for purpose of testing connection to local MongoDB
//  or can be adjusted to script that once for a while fetch offers from external service
@ShellComponent
public class CreateOfferCommand {

    @Autowired
    private OfferRepository repository;

    @ShellMethod(key="offer")
    public String create(String id) {
        Offer offer = new Offer(id, "test");
        repository.save(offer);
        return "Created";
    }
}
