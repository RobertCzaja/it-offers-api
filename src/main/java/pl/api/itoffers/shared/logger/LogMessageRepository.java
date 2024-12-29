package pl.api.itoffers.shared.logger;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogMessageRepository extends MongoRepository<LogMessage, UUID> {}
