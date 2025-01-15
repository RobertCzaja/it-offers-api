package pl.api.itoffers.offer.domain;

import java.time.LocalDateTime;

public record OfferMetadata(
    String technology,
    String slug,
    String title,
    String seniority,
    String workplace,
    String time,
    Boolean remoteInterview,
    LocalDateTime publishedAt) {}
