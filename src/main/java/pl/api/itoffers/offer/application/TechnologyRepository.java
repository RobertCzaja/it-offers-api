package pl.api.itoffers.offer.application;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository {
    List<String> allActive();
}
