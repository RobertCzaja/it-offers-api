package pl.api.itoffers.helper;

import pl.api.itoffers.offer.domain.*;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItDateTime;

import java.util.HashSet;
import java.util.Set;

public class OfferBuilder {

    private String technology;
    private Set<Category> categories = new HashSet<Category>();

    private void clearState() {
        categories = new HashSet<Category>();
        technology = null;
    }

    private void checkIfStateIsNotEmpty() {
        if (categories.isEmpty()) throw new NotCompletedException("categories");
        if (null == technology) throw new NotCompletedException("technology");
    }

    public OfferBuilder skills(String cat1, String cat2) {
        categories.add(new Category(cat1));
        categories.add(new Category(cat2));
        return this;
    }

    public OfferBuilder skills(String cat1, String cat2, String cat3) {
        categories.add(new Category(cat1));
        categories.add(new Category(cat2));
        categories.add(new Category(cat3));
        return this;
    }

    public OfferBuilder skills(String cat1, String cat2, String cat3, String cat4) {
        categories.add(new Category(cat1));
        categories.add(new Category(cat2));
        categories.add(new Category(cat3));
        categories.add(new Category(cat4));
        return this;
    }

    public OfferBuilder job(String positionMainTechnology) {
        technology = positionMainTechnology;
        return this;
    }

    public Offer build() {
        checkIfStateIsNotEmpty();
        Offer offer = new Offer(
                technology,
                "remitly-software-development-engineer-krakow-go-5fbdbda0",
                "Software Development Engineer",
                "mid",
                new Salary(Double.valueOf(14000),Double.valueOf(18000), "PLN", "b2b"),
                new Characteristics("hybrid","full_time", true),
                categories,
                new Company("creativestyle", "Kraków", "Zabłocie 25/1"),
                JustJoinItDateTime.createFrom("yyyy-MM-dd'T'HH:mm:ss.SSSX").value
        );
        clearState();
        return offer;
    }

    static class NotCompletedException extends RuntimeException {
        public NotCompletedException(String field) {
            super(field+" must be provided");
        }
    }
}
