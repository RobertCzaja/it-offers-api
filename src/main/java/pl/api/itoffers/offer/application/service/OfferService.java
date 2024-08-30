package pl.api.itoffers.offer.application.service;

import com.fasterxml.jackson.databind.JsonNode;
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

    public void processOffersFromExternalService(UUID scrappingId)
    {
        List<JustJoinItRawOffer> rawOffers = jjitRawOffersRepository.findByScrapingId(scrappingId);

        for (JustJoinItRawOffer rawOffer : rawOffers) {

            Map<String, Set<Category>> categories = createCategories(rawOffer);
            Company company = createCompany(rawOffer);
            Salary salary = createSalary(rawOffer);
            Offer offer = createOffer(rawOffer, salary, categories.get("forEntity"), company);

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
            Set<Category> categories,
            Company company
    ) {

        return new Offer(
                (String) rawOffer.getOffer().get("slug"),
                (String) rawOffer.getOffer().get("title"),
                (String) rawOffer.getOffer().get("experienceLevel"),
                salary,
                new Characteristics("hybrid","full_time", true),
                categories,
                company,
                JustJoinItDateTime.createFrom(
                        (String) rawOffer.getOffer().get("publishedAt")
                ).value
        );
    }

    private Salary createSalary(JustJoinItRawOffer rawOffer) {
        ArrayList<HashMap<String, Object>> employmentTypes = (ArrayList<HashMap<String, Object>>) rawOffer.getOffer().get("employmentTypes");
        HashMap<String, Object> employmentType = employmentTypes.get(0);
        return null != employmentType.get("from")
                ? new Salary(
                    Double.valueOf((Integer)employmentType.get("from")),
                    Double.valueOf((Integer)employmentType.get("to")),
                    (String) employmentType.get("currency"),
                    (String) employmentType.get("type")
                )
                : new Salary();
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

    private Company createCompany(JustJoinItRawOffer rawOffer) {
        String companyName = (String) rawOffer.getOffer().get("companyName");

        Company company = companyRepository.findByName(companyName);
        if (null == company) {
            company = new Company(
                    companyName,
                    (String) rawOffer.getOffer().get("city"),
                    (String) rawOffer.getOffer().get("street")
            );
        }

        return company;
    }
}
