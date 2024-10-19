package pl.api.itoffers.provider.justjoinit.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.api.itoffers.provider.justjoinit.model.CronLog;

import java.util.UUID;

@Repository
public interface CronLogRepository extends MongoRepository<CronLog, UUID> {
}
