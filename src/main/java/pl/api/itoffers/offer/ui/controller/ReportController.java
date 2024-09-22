package pl.api.itoffers.offer.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.offer.application.dto.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.incoming.CategoriesFilter;
import pl.api.itoffers.offer.application.service.ReportService;

import java.util.*;

@RestController
public class ReportController {
    public final static String PATH_CATEGORY = "/report/categories";

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(PATH_CATEGORY)
    public ResponseEntity<CategoriesStatisticsDto> categoriesReport(
        @RequestParam(required = false) String[] technologies,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateFrom,
        @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo
    ) {
        CategoriesFilter filter = new CategoriesFilter(technologies, dateFrom, dateTo);

        return new ResponseEntity<CategoriesStatisticsDto>(
            reportService.computeCategoriesStatistics(filter.getFrom(), filter.getTo(), filter.getTechnologies()),
            HttpStatus.OK
        );
    }
}
