package pl.api.itoffers.provider.justjoinit.service;

import java.util.ArrayList;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface JustJoinItOffersFetcher {
  ArrayList<Map<String, Object>> fetch(String technology);
}
