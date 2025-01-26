package pl.api.itoffers.helper.provider;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawDetailsOffer;
import pl.api.itoffers.provider.nofluffjobs.model.NoFluffJobsRawListOffer;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Service
@RequiredArgsConstructor
public class ProviderOfferOrchestrator {

  private final JustJoinItRepository justJoinItRepository;
  private final NoFluffJobsListOfferRepository noFluffJobsListOfferRepository;
  private final NoFluffJobsDetailsOfferRepository noFluffJobsDetailsOfferRepository;

  public void saveJJITListOffer(String technology) {
    justJoinItRepository.save(
        new JustJoinItRawOffer(
            UUID.randomUUID(), technology, new HashMap<>(), LocalDateTime.now()));
  }

  public void saveNFJListOffer(String technology) {
    noFluffJobsListOfferRepository.save(
        new NoFluffJobsRawListOffer(
            UUID.randomUUID(),
            UUID.randomUUID(),
            technology,
            new HashMap<>(),
            LocalDateTime.now()));
  }

  public void saveNFJDetailsOffer() {
    noFluffJobsDetailsOfferRepository.save(
        new NoFluffJobsRawDetailsOffer(
            UUID.randomUUID(), UUID.randomUUID(), new HashMap<>(), LocalDateTime.now()));
  }

  public void addSomeStateToMongoDbThatShouldBeAvoided() {
    saveJJITListOffer("java");
    saveJJITListOffer("php");
    saveNFJListOffer("java");
    saveNFJListOffer("php");
    saveNFJDetailsOffer();
  }
}
