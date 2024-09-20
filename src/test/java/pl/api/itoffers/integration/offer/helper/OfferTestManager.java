package pl.api.itoffers.integration.offer.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;

import java.util.List;

@Service
@Profile("test")
public class OfferTestManager {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private OfferRepository offerRepository;

    public void saveAll(List<Offer> offers) {
        for (Offer offer : offers) {
            companyRepository.save(offer.getCompany());
            categoryRepository.saveAll(offer.getCategories());
            offerRepository.save(offer);
        }
    }

    public void clearAll() {
        companyRepository.deleteAll();
        categoryRepository.deleteAll();
        offerRepository.deleteAll();
    }
}
