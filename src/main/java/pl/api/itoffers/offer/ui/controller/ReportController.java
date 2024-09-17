package pl.api.itoffers.offer.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.offer.application.service.ReportService;

/**
 * TODO add integration tests
 */
@RestController
public class ReportController {
    public final static String PATH_CATEGORY = "/report/categories";

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(PATH_CATEGORY)
    public ResponseEntity categoriesReport() {
        return new ResponseEntity<>(
            reportService.computeCategoriesStatistics(null, null),
            HttpStatus.CREATED
        );
    }

}
