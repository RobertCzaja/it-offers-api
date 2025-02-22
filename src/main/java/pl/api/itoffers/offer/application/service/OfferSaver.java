package pl.api.itoffers.offer.application.service;

import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.event.OfferSavingFailedEvent;
import pl.api.itoffers.offer.application.exception.DuplicatedOfferException;
import pl.api.itoffers.offer.application.factory.EventFactory;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Characteristics;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.offer.domain.OfferDraft;
import pl.api.itoffers.offer.domain.OfferMetadata;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.Origin;
import pl.api.itoffers.shared.utils.clock.ClockInterface;

@Service
@RequiredArgsConstructor
public class OfferSaver {

  private final CategoryRepository categoryRepository;
  private final CompanyRepository companyRepository;
  private final OfferRepository offerRepository;
  private final ClockInterface clock;
  private final EventFactory eventFactory;
  private final ApplicationEventPublisher publisher;

  public void save(OfferDraft draft) {
    try {
      var offer =
          prepareAndSave(
              draft.origin(),
              draft.metadata(),
              draft.categories(),
              draft.salaries(),
              draft.company());
      publisher.publishEvent(eventFactory.newOfferAddedEvent(offer));
    } catch (DuplicatedOfferException ignored) {
      // omitting already saved offers
    } catch (Exception e) {
      publisher.publishEvent(
          new OfferSavingFailedEvent(
              this,
              draft.origin().getScrappingId(),
              draft.metadata().technology(),
              draft.origin().getId(),
              e));
    }
  }

  private Offer prepareAndSave(
      Origin origin,
      OfferMetadata offerMetadata,
      Set<Category> categories,
      Set<Salary> salaries,
      Company company) {
    var categoryCollections = prepareCategories(categories);
    var preparedCompany = prepareCompany(company);

    var offer =
        new Offer(
            origin,
            offerMetadata.technology(),
            offerMetadata.slug(),
            offerMetadata.title(),
            offerMetadata.seniority(),
            new Characteristics(
                offerMetadata.workplace(), offerMetadata.time(), offerMetadata.remoteInterview()),
            categoryCollections.forEntity(),
            salaries,
            preparedCompany,
            offerMetadata.publishedAt(),
            clock.now());

    if (null != findAlreadyStoredOffer(offer)) {
      throw new DuplicatedOfferException();
    }

    companyRepository.save(preparedCompany);
    categoryRepository.saveAll(categoryCollections.toSave());
    offerRepository.save(offer);
    return offer;
  }

  private Offer findAlreadyStoredOffer(Offer offer) {
    return offerRepository.findByDifferentOffer(
        offer.getSlug(), offer.getTitle(), offer.getCompany().getName());
  }

  private CategoryCollections prepareCategories(Set<Category> categories) {
    var categoriesForEntity = new HashSet<Category>();
    var categoriesToSave = new HashSet<Category>();

    for (Category newCategory : categories) {
      Category category = categoryRepository.findByName(newCategory.getName());

      if (null == category) {
        category = newCategory;
        categoriesToSave.add(category);
      }

      categoriesForEntity.add(category);
    }
    return new CategoryCollections(categoriesForEntity, categoriesToSave);
  }

  private Company prepareCompany(Company newCompany) {
    Company savedCompany = companyRepository.findByName(newCompany.getName());
    return null == savedCompany ? newCompany : savedCompany;
  }
}
