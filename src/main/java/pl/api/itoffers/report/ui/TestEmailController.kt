package pl.api.itoffers.report.ui

import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.api.itoffers.report.service.statisticsNotifier.EmailStatisticsNotifier

@RestController
@Deprecated("should be removed after the implementation of #69")
@Profile("!test")
class TestEmailController (
    private val statisticsNotifier: EmailStatisticsNotifier
) {
    @GetMapping("/email")
    fun sendEmail(): String {
        statisticsNotifier.notify(
            mapOf(
                "title" to "✅ NO_FLUFF_JOBS Import",
                "scrapingId" to "12994bf4-a862-4f3f-8458-df8c3c10d765",
                "day" to "2025-02-01",
                "from" to "06:00:00",
                "to" to "06:00:44",
                "duration" to "0m 44s",
                "technologies" to mapOf(
                    "php" to mapOf("fetched" to 20, "new" to 0),
                    "java" to mapOf("fetched" to 20, "new" to 2),
                    "go" to mapOf("fetched" to 20, "new" to 0),
                    "devops" to mapOf("fetched" to 20, "new" to 20),
                ),
            )
        );
        return "Email sent";
    }
}