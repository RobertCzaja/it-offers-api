package pl.api.itoffers.helper;

import java.time.LocalDateTime;
import java.util.*;
import pl.api.itoffers.offer.application.repository.CategoryRepository;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.Origin;

public class OfferBuilder {

  private String technology;
  private Set<Category> categories = new HashSet<Category>();
  private Set<Salary> salaries = new HashSet<Salary>();

  /**
   * If it's set to true it will generate for each Category entity random UUID. It's important to
   * set it to false in integration testing because Category entity with already set UUID cannot be
   * forEntity in DB.
   */
  public boolean generateId = true;

  private Company company = new Company("creativestyle", "Kraków", "Zabłocie 25/1");
  private Origin origin = createRandomOrigin();
  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime publishedAt = LocalDateTime.now();

  private CategoryRepository categoryRepository;
  private CompanyRepository companyRepository;
  private OfferRepository offerRepository;

  public OfferBuilder() {}

  public OfferBuilder(
      CategoryRepository categoryRepository,
      CompanyRepository companyRepository,
      OfferRepository offerRepository) {
    this.categoryRepository = categoryRepository;
    this.companyRepository = companyRepository;
    this.offerRepository = offerRepository;
  }

  private void clearState() {
    origin = createRandomOrigin();
    categories = new HashSet<Category>();
    salaries = new HashSet<Salary>();
    technology = null;
    company = new Company("creativestyle", "Kraków", "Zabłocie 25/1");
    createdAt = LocalDateTime.now();
  }

  private void checkIfStateIsNotEmpty() {
    if (categories.isEmpty()) throw new NotCompletedException("categories");
    if (null == technology) throw new NotCompletedException("technology");
    if (null == company) throw new NotCompletedException("company");
    if (null == createdAt) throw new NotCompletedException("createdAt");
  }

  private static Origin createRandomOrigin() {
    return new Origin(
        UUID.randomUUID().toString().substring(0, 25),
        UUID.randomUUID(),
        Origin.Provider.JUST_JOIN_IT);
  }

  private Category createCategory(String name) {
    Category category = new Category(name);
    if (generateId) {
      category.setId(UUID.randomUUID());
    }
    return category;
  }

  public void notGenerateEntityIdsBecauseTheseShouldBeGeneratedByJPA() {
    this.generateId = false;
  }

  /**
   * @param monthAndDay in format MM-dd
   */
  public OfferBuilder at(String monthAndDay) {
    createdAt = LocalDateTimeCustomBuilder.createFromDate("2024-" + monthAndDay);
    publishedAt = createdAt;
    return this;
  }

  public OfferBuilder pln(int from, int to, String employmentType) {
    salaries.add(new Salary(new SalaryAmount(from, to, "PLN"), employmentType, false));
    return this;
  }

  public OfferBuilder pln(int from, int to) {
    pln(from, to, "b2b");
    return this;
  }

  public OfferBuilder usd(int from, int to) {
    salaries.add(new Salary(new SalaryAmount(from, to, "USD"), "b2b", false));
    return this;
  }

  public OfferBuilder skills(String cat1) {
    categories.add(createCategory(cat1));
    return this;
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

  public OfferBuilder plainJob(String technology) {
    this.technology = technology;
    skills(technology);
    return this;
  }

  public OfferBuilder job(String positionMainTechnology) {
    technology = positionMainTechnology;
    return this;
  }

  public OfferBuilder offer(String technology, String skill) {
    job(technology);
    skills(skill);
    return this;
  }

  private static Offer createOffer(
      Origin origin,
      String technology,
      Company company,
      Set<Category> categories,
      Set<Salary> salaries,
      LocalDateTime createdAt,
      LocalDateTime publishedAt) {
    return new Offer(
        origin,
        technology,
        "remitly-software-development-engineer-krakow-go-5fbdbda0",
        "Software Development Engineer",
        "mid",
        new Characteristics("hybrid", "full_time", true),
        categories,
        salaries,
        company,
        publishedAt,
        createdAt);
  }

  /** TODO: duplicated, should be merged into one */
  public Offer build() {
    checkIfStateIsNotEmpty();
    Offer offer =
        createOffer(origin, technology, company, categories, salaries, createdAt, publishedAt);
    clearState();
    return offer;
  }

  /** TODO: duplicated, should be merged into one */
  public void save() {
    checkIfStateIsNotEmpty();
    offerRepository.save(
        createOffer(
            origin, technology, saveCompany(), saveCategories(), salaries, createdAt, publishedAt));
    clearState();
  }

  /** TODO: duplicated, should be merged into one */
  public Offer saveAndGetOffer() {
    checkIfStateIsNotEmpty();

    Offer offer =
        createOffer(
            origin, technology, saveCompany(), saveCategories(), salaries, createdAt, publishedAt);

    offerRepository.save(offer);
    clearState();
    return offer;
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
      super(field + " must be provided");
    }
  }
}
