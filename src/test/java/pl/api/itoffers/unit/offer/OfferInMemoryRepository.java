package pl.api.itoffers.unit.offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import pl.api.itoffers.offer.application.repository.OfferRepository;
import pl.api.itoffers.offer.domain.Offer;

public class OfferInMemoryRepository implements OfferRepository {

  public List<Offer> offers = List.of();

  @Override
  public List<Offer> findByCreatedAtBetween(
      String[] technologies, LocalDateTime from, LocalDateTime to) {
    return offers;
  }

  @Override
  public Offer findByDifferentOffer(String slug, String title, String companyName) {
    return null;
  }

  @Override
  public List<Offer> findByPublishedAt(LocalDateTime publishedAt) {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public List<Offer> findAllByOrderBySlugAsc() {
    throw new RuntimeException("Not implemented");
  }

  @Override
  public void flush() {}

  @Override
  public <S extends Offer> S saveAndFlush(S entity) {
    return null;
  }

  @Override
  public <S extends Offer> List<S> saveAllAndFlush(Iterable<S> entities) {
    return List.of();
  }

  @Override
  public void deleteAllInBatch(Iterable<Offer> entities) {}

  @Override
  public void deleteAllByIdInBatch(Iterable<UUID> uuids) {}

  @Override
  public void deleteAllInBatch() {}

  @Override
  public Offer getOne(UUID uuid) {
    return null;
  }

  @Override
  public Offer getById(UUID uuid) {
    return null;
  }

  @Override
  public Offer getReferenceById(UUID uuid) {
    return null;
  }

  @Override
  public <S extends Offer> Optional<S> findOne(Example<S> example) {
    return Optional.empty();
  }

  @Override
  public <S extends Offer> List<S> findAll(Example<S> example) {
    return List.of();
  }

  @Override
  public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
    return List.of();
  }

  @Override
  public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
    return null;
  }

  @Override
  public <S extends Offer> long count(Example<S> example) {
    return 0;
  }

  @Override
  public <S extends Offer> boolean exists(Example<S> example) {
    return false;
  }

  @Override
  public <S extends Offer, R> R findBy(
      Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
    return null;
  }

  @Override
  public <S extends Offer> S save(S entity) {
    return null;
  }

  @Override
  public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
    return List.of();
  }

  @Override
  public Optional<Offer> findById(UUID uuid) {
    return Optional.empty();
  }

  @Override
  public boolean existsById(UUID uuid) {
    return false;
  }

  @Override
  public List<Offer> findAll() {
    return List.of();
  }

  @Override
  public List<Offer> findAllById(Iterable<UUID> uuids) {
    return List.of();
  }

  @Override
  public long count() {
    return 0;
  }

  @Override
  public void deleteById(UUID uuid) {}

  @Override
  public void delete(Offer entity) {}

  @Override
  public void deleteAllById(Iterable<? extends UUID> uuids) {}

  @Override
  public void deleteAll(Iterable<? extends Offer> entities) {}

  @Override
  public void deleteAll() {}

  @Override
  public List<Offer> findAll(Sort sort) {
    return List.of();
  }

  @Override
  public Page<Offer> findAll(Pageable pageable) {
    return null;
  }
}
