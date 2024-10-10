package pl.api.itoffers.offer.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinFormula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @Getter
    private final String technology;
    @Getter
    private final String slug;
    @Getter
    private final String title;
    private final String seniority;
    @Embedded
    private final DeprecatedSalary deprecatedSalary;
    @Embedded
    private final Characteristics characteristics;
    @Getter
    @ManyToMany
    private final Set<Category> categories;
    @NonNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Salary> salary;
    @Getter
    @ManyToOne
    private final Company company;
    @Getter
    private final LocalDateTime publishedAt;
    @Getter
    private final LocalDateTime createdAt;

    /**
     * Should be removed after Salaries migration
     */
    @Deprecated
    public void setSalaries(Set<Salary> salaries) {
        this.salary = salaries;
    }

    public Set<Salary> getSalaries() {
        return salary;
    }

    @Override
    public String toString() {
        return title + " (" + slug + ") " + company.getName() + " publishedAt: " + publishedAt;
    }
}
