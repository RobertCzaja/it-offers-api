package pl.api.itoffers.provider.justjoinit.infrastructure;

import org.bson.Document;
import pl.api.itoffers.provider.justjoinit.JustJoinItRepository;

public class JustJoinItMongoRepository implements JustJoinItRepository {

    public void save(Document document)
    {
        throw new RuntimeException("Use Hibernate instead"); // TODO to remove, shouldn't be used
    }
}
