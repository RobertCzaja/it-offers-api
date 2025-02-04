package pl.api.itoffers.report.service.statisticsNotifier

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import pl.api.itoffers.report.service.EmailTemplateConverter
import pl.api.itoffers.report.service.StatisticsNotifier

@Primary
@Service
@Profile("!test")
class EmailStatisticsNotifier(
    private val mailSender: JavaMailSender,
    private val emailTemplateConverter: EmailTemplateConverter
): StatisticsNotifier {

    @Value("\${application.report.destinationEmail}")
    private val reportToEmail: String? = null

    override fun notify(reportDetails: Map<String, Any>) {
        if (null == reportToEmail) {
            throw RuntimeException("Reporter email needs to be provided")
        }

        val title = reportDetails["title"] as? String ?: "Import report"

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        helper.setTo(reportToEmail)
        helper.setSubject(title)
        helper.setText(emailTemplateConverter.convert(reportDetails), true)

        mailSender.send(message)
    }
}