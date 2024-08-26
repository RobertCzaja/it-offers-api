package pl.api.itoffers.offer.application.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository {
    List<String> allActive();
}
