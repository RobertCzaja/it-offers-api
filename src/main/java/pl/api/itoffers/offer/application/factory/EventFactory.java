package pl.api.itoffers.offer.application.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.event.NewOfferAddedEvent;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.ProviderFacade;

@Service
@RequiredArgsConstructor
public class EventFactory {

  private final ProviderFacade providerFacade;

  public NewOfferAddedEvent newOfferAddedEvent(Offer offer) {

    var salary = offer.getSalaries().stream().findFirst();
    var salaryAmount = salary.map(Salary::getAmount).orElse(null);

    return new NewOfferAddedEvent(
        this,
        offer.getOrigin().getScrappingId(),
        offer.getTechnology(),
        providerFacade.getOfferUrl(offer),
        offer.getTitle(),
        null != salaryAmount ? salaryAmount.getCurrency() : null,
        null != salaryAmount ? salaryAmount.getFrom() : null,
        null != salaryAmount ? salaryAmount.getTo() : null);
  }
}
