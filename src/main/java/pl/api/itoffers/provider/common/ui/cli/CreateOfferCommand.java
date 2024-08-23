package pl.api.itoffers.provider.common.ui.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import pl.api.itoffers.provider.common.domain.Offer;
import pl.api.itoffers.provider.common.application.OfferRepository;

// TODO it's only testing CLI to check MongoDB connection - it should be removed
@Deprecated
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
