package pl.api.itoffers.security.ui.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.api.itoffers.shared.logger.Logger;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TestController {
    public final static String PATH = "/test";

    private final Logger logger;

    @GetMapping(PATH)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> test(
        @RequestParam(required = false, defaultValue = "") String message
    ) {
        if (message.equals("exception")) {
            throw new RuntimeException("test666");
        } else {
            log.info("Test endpoint: {}", message);
        }

        return new ResponseEntity<>("test", HttpStatus.OK);
    }
}
