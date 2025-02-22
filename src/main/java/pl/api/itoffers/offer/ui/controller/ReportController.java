package pl.api.itoffers.offer.ui.controller;

import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.offer.application.dto.incoming.CategoriesFilter;
import pl.api.itoffers.offer.application.dto.incoming.DatesRangeFilter;
import pl.api.itoffers.offer.application.dto.outgoing.CategoriesStatisticsDto;
import pl.api.itoffers.offer.application.dto.outgoing.offersalaries.OffersSalariesDto;
import pl.api.itoffers.offer.application.factory.TechnologiesFilterFactory;
import pl.api.itoffers.offer.application.service.ReportCategoriesService;
import pl.api.itoffers.offer.application.service.ReportSalariesService;

@RestController
@RequiredArgsConstructor
public class ReportController {
  public static final String PATH_CATEGORY = "/report/categories";
  public static final String PATH_SALARIES = "/report/salaries";

  private final ReportCategoriesService reportCategoriesService;
  private final ReportSalariesService reportSalariesService;
  private final TechnologiesFilterFactory technologiesFilterFactory;

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(PATH_CATEGORY)
  public ResponseEntity<CategoriesStatisticsDto> categoriesReport(
      @RequestParam(required = false) String[] technologies,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
    CategoriesFilter filter = new CategoriesFilter(technologies, dateFrom, dateTo);

    return new ResponseEntity<>(
        reportCategoriesService.computeCategoriesStatistics(
            filter.getTechnologies(), filter.getFrom(), filter.getTo()),
        HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @GetMapping(PATH_SALARIES)
  public ResponseEntity<OffersSalariesDto> salariesReport(
      @RequestParam(required = false, defaultValue = "PLN") String currency,
      @RequestParam(required = false, defaultValue = "b2b") String employmentType,
      @RequestParam(required = false, defaultValue = "0") String to,
      @RequestParam(required = false) String[] technologies,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo) {
    return new ResponseEntity<>(
        new OffersSalariesDto(
            reportSalariesService.getMostPaidOffers(
                currency,
                employmentType,
                Integer.parseInt(to),
                technologiesFilterFactory.get(technologies),
                new DatesRangeFilter(dateFrom, dateTo))),
        HttpStatus.OK);
  }
}
