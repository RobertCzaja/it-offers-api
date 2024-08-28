package pl.api.itoffers.offer.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Category;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Offer;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OfferService {

    @Autowired
    private JustJoinItRepository jjitRawOffersRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private OfferRepository offerRepository;

    public void processOffersFromExternalService(UUID scrappingId)
    {
        List<JustJoinItRawOffer> rawOffers = jjitRawOffersRepository.findByScrapingId(scrappingId);

        for (JustJoinItRawOffer rawOffer : rawOffers) {

            List<String> requiredSkills = (List<String>) rawOffer.getOffer().get("requiredSkills");
            Set<Category> categories = new HashSet<Category>();

            for (String requiredSkill : requiredSkills) {
                categories.add(new Category(requiredSkill));
            }

            Company company = new Company(
                    (String) rawOffer.getOffer().get("companyName"),
                    (String) rawOffer.getOffer().get("city"),
                    (String) rawOffer.getOffer().get("street")
            );

            Offer offer = new Offer(
                    (String) rawOffer.getOffer().get("slug"),
                    (String) rawOffer.getOffer().get("title"),
                    categories,
                    company,
                    JustJoinItDateTime.createFrom(
                            (String) rawOffer.getOffer().get("publishedAt")
                    ).value
            );

            // todo does not allow to companies/categories duplications

            companyRepository.save(company);
            categoryRepository.saveAll(categories);
            offerRepository.save(offer);
        }
    }
}
