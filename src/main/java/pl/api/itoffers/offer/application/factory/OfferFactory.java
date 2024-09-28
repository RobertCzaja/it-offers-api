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
            Company company
    ) {

        return new Offer(
            rawOffer.getTechnology(),
            (String) rawOffer.getOffer().get("slug"),
            (String) rawOffer.getOffer().get("title"),
            (String) rawOffer.getOffer().get("experienceLevel"),
            createSalary(rawOffer),
            createCharacteristics(rawOffer),
            categories,
            null, // todo maybe that could be removed? // todo Salaries are not saved yet, supposed to be a real collection?
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

    private DeprecatedSalary createSalary(JustJoinItRawOffer rawOffer) {
        ArrayList<HashMap<String, Object>> employmentTypes = (ArrayList<HashMap<String, Object>>) rawOffer.getOffer().get("employmentTypes");
        HashMap<String, Object> employmentType = employmentTypes.get(0);
        return null != employmentType.get("from")
                ? new DeprecatedSalary(
                Double.valueOf((Integer)employmentType.get("from")),
                Double.valueOf((Integer)employmentType.get("to")),
                (String) employmentType.get("currency"),
                (String) employmentType.get("type")
        )
                : new DeprecatedSalary();
    }

    private Characteristics createCharacteristics(JustJoinItRawOffer rawOffer) {
        return new Characteristics(
                (String) rawOffer.getOffer().get("workplaceType"),
                (String) rawOffer.getOffer().get("workingTime"),
                (Boolean) rawOffer.getOffer().get("remoteInterview")
        );
    }
}
