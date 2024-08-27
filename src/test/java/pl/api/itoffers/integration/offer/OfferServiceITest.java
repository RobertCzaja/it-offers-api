package pl.api.itoffers.integration.offer;

import org.junit.jupiter.api.Test;
import pl.api.itoffers.helper.AbstractITest;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferServiceITest extends AbstractITest {

    @Test
    public void ______() {

        /*
        fetch and save in MongoDB first set of JJIT Offers (UUID: A, all new offers)
        fetch and save in MongoDb second set of JJIT Offers (UUID: B, some new offers, some duplicated)
        run new service to save MongoDB offers in Postgres
        Assertions:
        check for unique: Offers/Categories/Companies
        */



        assertThat("").isNotNull();
    }
}
