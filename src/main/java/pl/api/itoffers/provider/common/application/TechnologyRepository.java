package pl.api.itoffers.provider.common.application;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnologyRepository {
    List<String> allActive();
}
