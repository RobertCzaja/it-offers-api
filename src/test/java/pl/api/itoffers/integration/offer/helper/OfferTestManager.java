package pl.api.itoffers.integration.offer.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.api.itoffers.helper.OfferBuilder;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.provider.justjoinit.repository.JustJoinItRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsDetailsOfferRepository;
import pl.api.itoffers.provider.nofluffjobs.repository.NoFluffJobsListOfferRepository;

@Service
@Profile("test")
public class OfferTestManager {

  @Autowired private CategoryRepository categoryRepository;
  @Autowired private CompanyRepository companyRepository;
  @Autowired private OfferRepository offerRepository;
  @Autowired private NoFluffJobsDetailsOfferRepository noFluffJobsDetailsOfferRepository;
  @Autowired private NoFluffJobsListOfferRepository noFluffJobsListOfferRepository;
  @Autowired private JustJoinItRepository jjitRawOffersRepository;

  public OfferBuilder createOfferBuilder() {
    return new OfferBuilder(categoryRepository, companyRepository, offerRepository);
  }

  public void clearAll() {
    offerRepository.deleteAll();
    categoryRepository.deleteAll();
    companyRepository.deleteAll();
    noFluffJobsDetailsOfferRepository.deleteAll();
    noFluffJobsListOfferRepository.deleteAll();
    jjitRawOffersRepository.deleteAll();
  }
}
