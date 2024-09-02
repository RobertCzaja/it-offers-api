package pl.api.itoffers.offer.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.*;

@Slf4j
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
    @Autowired
    private OfferFactory offerFactory;

    public void processOffersFromExternalService(UUID scrappingId)
    {
        List<JustJoinItRawOffer> rawOffers = jjitRawOffersRepository.findByScrapingId(scrappingId);

        for (JustJoinItRawOffer rawOffer : rawOffers) {

            Map<String, Set<Category>> categories = createCategories(rawOffer);
            Company company = offerFactory.createCompany(rawOffer);
            Salary salary = offerFactory.createSalary(rawOffer);
            Characteristics characteristics = createCharacteristics(rawOffer);
            Offer offer = createOffer(rawOffer, salary, characteristics, categories.get("forEntity"), company);

            Offer alreadyStoredOffer = findAlreadyStoredOffer(offer);

            if (null != alreadyStoredOffer) {
                continue;
            }

            companyRepository.save(company);
            categoryRepository.saveAll(categories.get("toSave"));
            offerRepository.save(offer);
            log.info(String.format("[just-join-it][migration] new offer %s", offer));
        }
    }

    private Offer findAlreadyStoredOffer(Offer offer) {
        return offerRepository.findByDifferentOffer(
                offer.getSlug(),
                offer.getTitle(),
                offer.getCompany().getName(),
                offer.getPublishedAt()
        );
    }

    private Offer createOffer(
            JustJoinItRawOffer rawOffer,
            Salary salary,
            Characteristics characteristics,
            Set<Category> categories,
            Company company
    ) {

        return new Offer(
                rawOffer.getTechnology(),
                (String) rawOffer.getOffer().get("slug"),
                (String) rawOffer.getOffer().get("title"),
                (String) rawOffer.getOffer().get("experienceLevel"),
                salary,
                characteristics,
                categories,
                company,
                JustJoinItDateTime.createFrom(
                        (String) rawOffer.getOffer().get("publishedAt")
                ).value
        );
    }

    private Characteristics createCharacteristics(JustJoinItRawOffer rawOffer) {
        return new Characteristics(
                (String) rawOffer.getOffer().get("workplaceType"),
                (String) rawOffer.getOffer().get("workingTime"),
                (Boolean) rawOffer.getOffer().get("remoteInterview")
        );
    }

    private Map<String, Set<Category>> createCategories(JustJoinItRawOffer rawOffer) {
        List<String> requiredSkills = (List<String>) rawOffer.getOffer().get("requiredSkills");
        Map<String, Set<Category>> result = new HashMap<String, Set<Category>>();
        Set<Category> categories = new HashSet<Category>();
        Set<Category> categoriesToSave = new HashSet<Category>();

        for (String requiredSkill : requiredSkills) {
            Category category = categoryRepository.findByName(requiredSkill);

            if (null == category) {
                category = new Category(requiredSkill);
                categoriesToSave.add(category);
            }
            categories.add(category);
        }
        result.put("forEntity", categories);
        result.put("toSave", categoriesToSave);
        return result;
    }
}
