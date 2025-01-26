package pl.api.itoffers.offer.domain;

import java.util.Set;
import pl.api.itoffers.provider.Origin;

public record OfferDraft(
    Origin origin,
    OfferMetadata metadata,
    Set<Category> categories,
    Set<Salary> salaries,
    Company company) {}
