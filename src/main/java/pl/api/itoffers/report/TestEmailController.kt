package pl.api.itoffers.report

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * TODO should be removed after the implementation of #69
 */
@RestController
class TestEmailController (
    private val emailNotifier: EmailStatisticsNotifier
) {
    @GetMapping("/email")
    fun sendEmail(): String {
        emailNotifier.notify("TestEmail")
        return "Email sent";
    }
}