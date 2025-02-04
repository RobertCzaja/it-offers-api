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
        statisticsNotifier.notifyDeprecated("TestEmail")
        return "Email sent";
    }
}