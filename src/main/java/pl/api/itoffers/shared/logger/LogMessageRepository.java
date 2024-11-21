package pl.api.itoffers.shared.logger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogMessageRepository extends MongoRepository<LogMessage, UUID> {
}
