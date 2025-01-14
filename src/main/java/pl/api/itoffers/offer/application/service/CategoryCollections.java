package pl.api.itoffers.offer.application.service;

import java.util.Set;
import pl.api.itoffers.offer.domain.Category;

public record CategoryCollections(Set<Category> forEntity, Set<Category> toSave) {}
