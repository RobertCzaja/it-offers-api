package pl.api.itoffers.provider.justjoinit;

import org.bson.Document;

public class JustJoinItMongoRepository implements JustJoinItRepository {

    public void save(Document document)
    {
        throw new RuntimeException("Use Hibernate instead"); // TODO to remove, shouldn't be used
    }
}
