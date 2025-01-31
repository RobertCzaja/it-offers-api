package pl.api.itoffers.report.ui

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.api.itoffers.report.service.EmailStatisticsNotifier

/**
 * TODO should be removed after the implementation of #69
 */
@RestController
class TestEmailController (
    //private val statisticsNotifier: StatisticsNotifier
    private val emailStatisticsNotifier: EmailStatisticsNotifier
) {
    @GetMapping("/email")
    fun sendEmail(): String {
        //statisticsNotifier.notify("TestEmail")
        return "Email sent";
    }
}