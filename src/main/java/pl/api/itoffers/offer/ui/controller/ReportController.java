package pl.api.itoffers.offer.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.incoming.CategoriesFilter;
import pl.api.itoffers.offer.application.dto.outgoing.OffersDto;
import pl.api.itoffers.offer.application.service.ReportCategoriesService;
import pl.api.itoffers.offer.application.service.ReportSalariesService;

import java.util.*;

@RestController
public class ReportController {
    public final static String PATH_CATEGORY = "/report/categories";
    public final static String PATH_SALARIES = "/report/salaries";

    @Autowired
    private ReportCategoriesService reportCategoriesService;
    @Autowired
    private ReportSalariesService reportSalariesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(PATH_CATEGORY)
    public ResponseEntity<CategoriesStatisticsDto> categoriesReport(
        @RequestParam(required = false) String[] technologies,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo
    ) {
        CategoriesFilter filter = new CategoriesFilter(technologies, dateFrom, dateTo);

        return new ResponseEntity<CategoriesStatisticsDto>(
            reportCategoriesService.computeCategoriesStatistics(filter.getFrom(), filter.getTo(), filter.getTechnologies()),
            HttpStatus.OK
        );
    }

    /**
     * TODO
     *  add technologies filter
     *  add dates filter
     *  add salaries filter
     *  add option that consider "from"/"to" ("to" is default) as a value that orders that TOP salaries
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(PATH_SALARIES)
    public ResponseEntity<OffersDto> salariesReport(
        @RequestParam(required = false, defaultValue = "PLN") String currency,
        @RequestParam(required = false, defaultValue = "b2b") String employmentType,
        @RequestParam(required = false, defaultValue = "0") String to
    ) {
        return new ResponseEntity<OffersDto>(
            new OffersDto(reportSalariesService.getMostPaidOffers(currency, employmentType, Integer.parseInt(to))),
            HttpStatus.OK
        );
    }
}
