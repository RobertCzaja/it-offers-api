package pl.api.itoffers.provider.justjoinit.model;

import pl.api.itoffers.offer.domain.DeprecatedSalary;

import java.time.Instant;
import java.util.ArrayList;

/* TODO to remove - it's used only in the Factory which is gonna be removed */
@Deprecated
public class JustJoinItOffer {

    private final String slug;
    private final String title;
    private final ArrayList<String> requiredSkills;
    private final String experienceLevel;
    private final Instant publishedAt;
    private final String companyName;
    private final ArrayList<DeprecatedSalary> salaries;
    private final String workplaceType;

    public JustJoinItOffer(
            String slug,
            String title,
            ArrayList<String> requiredSkills,
            String experienceLevel,
            Instant publishedAt,
            String companyName,
            ArrayList<DeprecatedSalary> salaries,
            String workplaceType
    ) {
        this.slug = slug;
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.experienceLevel = experienceLevel;
        this.publishedAt = publishedAt;
        this.companyName = companyName;
        this.salaries = salaries;
        this.workplaceType = workplaceType;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getRequiredSkills() {
        return requiredSkills;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<DeprecatedSalary> getSalaries() {
        return salaries;
    }

    public String getWorkplaceType() {
        return workplaceType;
    }
}
