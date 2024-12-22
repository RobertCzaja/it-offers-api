package pl.api.itoffers.provider.justjoinit.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface JustJoinItOffersFetcher {
    ArrayList<Map<String, Object>> fetch(String technology);
}
