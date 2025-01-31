package pl.api.itoffers.report.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service


class EmailStatisticsNotifier(
    //private val mailSender: JavaMailSender
) /*: StatisticsNotifier*/ {

    @Value("\${application.report.destinationEmail}")
    private val reportToEmail: String? = null

    /*override*/ fun notify(report: String) {

//        val emailTitle = "It Offers Import"
//        if (null == reportToEmail) {
//            throw RuntimeException("Reporter email needs to be provided")
//        }
//
//        val message = mailSender.createMimeMessage()
//        val helper = MimeMessageHelper(message, true)
//
//        helper.setTo(reportToEmail)
//        helper.setSubject(emailTitle)
//        helper.setText(report, true)
//
//        mailSender.send(message)
    }
}