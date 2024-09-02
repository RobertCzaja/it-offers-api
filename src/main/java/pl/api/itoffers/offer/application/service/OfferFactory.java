package pl.api.itoffers.offer.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.api.itoffers.offer.application.repository.CompanyRepository;
import pl.api.itoffers.offer.domain.Company;
import pl.api.itoffers.offer.domain.Salary;
import pl.api.itoffers.provider.justjoinit.model.JustJoinItRawOffer;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class OfferFactory {
    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(JustJoinItRawOffer rawOffer) {
        String companyName = (String) rawOffer.getOffer().get("companyName");

        Company company = companyRepository.findByName(companyName);
        if (null == company) {
            company = new Company(
                    companyName,
                    (String) rawOffer.getOffer().get("city"),
                    (String) rawOffer.getOffer().get("street")
            );
        }

        return company;
    }

    public Salary createSalary(JustJoinItRawOffer rawOffer) {
        ArrayList<HashMap<String, Object>> employmentTypes = (ArrayList<HashMap<String, Object>>) rawOffer.getOffer().get("employmentTypes");
        HashMap<String, Object> employmentType = employmentTypes.get(0);
        return null != employmentType.get("from")
                ? new Salary(
                Double.valueOf((Integer)employmentType.get("from")),
                Double.valueOf((Integer)employmentType.get("to")),
                (String) employmentType.get("currency"),
                (String) employmentType.get("type")
        )
                : new Salary();
    }
}
