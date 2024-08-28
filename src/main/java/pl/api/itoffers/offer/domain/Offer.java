package pl.api.itoffers.offer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@RequiredArgsConstructor
public class Offer {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private final String slug;
    private final String title;
    @ManyToMany
    private final Set<Category> categories;
    @ManyToOne
    private final Company company;
    private final LocalDateTime publishedAt;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
