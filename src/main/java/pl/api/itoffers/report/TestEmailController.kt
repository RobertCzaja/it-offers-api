package pl.api.itoffers.report

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * TODO should be removed after the implementation of #69
 */
@RestController
class TestEmailController (
    private val mailSender: JavaMailSender
) {

    @GetMapping("/email")
    fun sendEmail(): String {
        return "Email sent";
    }
}