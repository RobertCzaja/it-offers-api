package pl.api.itoffers.offer.application.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OfferFactory {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Offer createOffer(
            JustJoinItRawOffer rawOffer,
            Set<Category> categories,
            Set<Salary> salaries,
            Company company
    ) {
        return new Offer(
            new Origin(rawOffer.getId().toString(), rawOffer.getScrapingId(), Origin.Provider.JUST_JOIN_IT),
            rawOffer.getTechnology(),
            (String) rawOffer.getOffer().get("slug"),
            (String) rawOffer.getOffer().get("title"),
            (String) rawOffer.getOffer().get("experienceLevel"),
            createCharacteristics(rawOffer),
            categories,
            salaries,
            company,
            JustJoinItDateTime.createFrom(
                (String) rawOffer.getOffer().get("publishedAt")
            ).value,
            LocalDateTime.now()
        );
    }

    public Map<String, Set<Category>> createCategories(JustJoinItRawOffer rawOffer) {
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

    public Company createCompany(JustJoinItRawOffer rawOffer) {
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

    private Characteristics createCharacteristics(JustJoinItRawOffer rawOffer) {
        return new Characteristics(
                (String) rawOffer.getOffer().get("workplaceType"),
                (String) rawOffer.getOffer().get("workingTime"),
                (Boolean) rawOffer.getOffer().get("remoteInterview")
        );
    }
}
