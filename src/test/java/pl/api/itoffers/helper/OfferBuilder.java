package pl.api.itoffers.helper;

import org.springframework.beans.factory.annotation.Autowired;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;

import java.util.*;

public class OfferBuilder {

    private String technology;
    private Set<Category> categories = new HashSet<Category>();
    public boolean generateId = true;
    private Company company = new Company("creativestyle", "Kraków", "Zabłocie 25/1");

    private CategoryRepository categoryRepository;
    private CompanyRepository companyRepository;
    private OfferRepository offerRepository;

    public OfferBuilder() {}

    public OfferBuilder(
        CategoryRepository categoryRepository,
        CompanyRepository companyRepository,
        OfferRepository offerRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.companyRepository = companyRepository;
        this.offerRepository = offerRepository;
    }

    private void clearState() {
        categories = new HashSet<Category>();
        technology = null;
    }

    private void checkIfStateIsNotEmpty() {
        if (categories.isEmpty()) throw new NotCompletedException("categories");
        if (null == technology) throw new NotCompletedException("technology");
        if (null == company) throw new NotCompletedException("company");
    }

    private Category createCategory(String name) {
        Category category = new Category(name);
        if (generateId) {
            category.setId(UUID.randomUUID());
        }
        return category;
    }

    public OfferBuilder skills(String cat1, String cat2) {
        categories.add(createCategory(cat1));
        categories.add(createCategory(cat2));
        return this;
    }

    public OfferBuilder skills(String cat1, String cat2, String cat3) {
        categories.add(createCategory(cat1));
        categories.add(createCategory(cat2));
        categories.add(createCategory(cat3));
        return this;
    }

    public OfferBuilder skills(String cat1, String cat2, String cat3, String cat4) {
        categories.add(createCategory(cat1));
        categories.add(createCategory(cat2));
        categories.add(createCategory(cat3));
        categories.add(createCategory(cat4));
        return this;
    }

    public OfferBuilder job(String positionMainTechnology) {
        technology = positionMainTechnology;
        return this;
    }

    private static Offer createOffer(
        String technology,
        Company company,
        Set<Category> categories
    ) {
        return new Offer(
            technology,
            "remitly-software-development-engineer-krakow-go-5fbdbda0",
            "Software Development Engineer",
            "mid",
            new Salary(Double.valueOf(14000),Double.valueOf(18000), "PLN", "b2b"),
            new Characteristics("hybrid","full_time", true),
            categories,
            company,
            JustJoinItDateTime.createFrom().value
        );
    }

    public Offer build() {
        checkIfStateIsNotEmpty();
        Offer offer = createOffer(
            technology,
            company,
            categories
        );
        clearState();
        return offer;
    }

    public void save() {
        checkIfStateIsNotEmpty();
        offerRepository.save(
            createOffer(
                technology,
                saveCompany(),
                saveCategories()
            )
        );
        clearState();
    }

    private Company saveCompany() {
        Company companyToSave = companyRepository.findByName(company.getName());
        if (null == companyToSave) {
            companyToSave = company;
        }
        companyRepository.save(companyToSave);
        return companyToSave;
    }

    private Set<Category> saveCategories() {
        Set<Category> categoriesToSave = new HashSet<>();
        Set<Category> categoriesForEntity = new HashSet<>();
        for (Category category : categories) {
            Category newCategory = categoryRepository.findByName(category.getName());
            if (null == newCategory) {
                newCategory = new Category(category.getName());
                categoriesToSave.add(newCategory);
            }
            categoriesForEntity.add(newCategory);
        }
        categoryRepository.saveAll(categoriesToSave);
        return categoriesForEntity;
    }

    static class NotCompletedException extends RuntimeException {
        public NotCompletedException(String field) {
            super(field+" must be provided");
        }
    }
}
