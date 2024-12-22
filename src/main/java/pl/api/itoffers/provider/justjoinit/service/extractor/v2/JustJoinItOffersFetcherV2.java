package pl.api.itoffers.provider.justjoinit.service.extractor.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.api.itoffers.provider.justjoinit.service.JustJoinItOffersFetcher;

import java.util.ArrayList;
import java.util.Map;

/**
 * Valid since 12.2024
 */
@Service
@RequiredArgsConstructor
public class JustJoinItOffersFetcherV2 implements JustJoinItOffersFetcher {
    @Override
    public ArrayList<Map<String, Object>> fetch(String technology) {
        // TODO to implement
        return null;
    }
}
