package pl.api.itoffers.report

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import pl.api.itoffers.shared.utils.clock.ClockInterface

@Configuration
open class StatisticsNotifierConfig {

//    @Bean
//    open fun testEmailController(): TestEmailController {
//        return TestEmailController(InMemoryStatisticsNotifier());
//    }

    @Bean
    open fun emailStatisticsNotifier(/*javaMailSender: JavaMailSender*/): EmailStatisticsNotifier {
        return EmailStatisticsNotifier(/*javaMailSender*/)
    }

    @Bean
    open fun importStatistics(clock: ClockInterface): ImportStatistics {
        return ImportStatistics(clock, InMemoryStatisticsNotifier())
    }
}