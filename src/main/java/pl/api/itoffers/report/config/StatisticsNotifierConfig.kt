package pl.api.itoffers.report.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.api.itoffers.report.service.EmailStatisticsNotifier
import pl.api.itoffers.report.service.ImportStatistics
import pl.api.itoffers.report.service.InMemoryStatisticsNotifier
import pl.api.itoffers.shared.utils.clock.ClockInterface

@Configuration
open class StatisticsNotifierConfig {

//    @Bean
//    open fun testEmailController(): TestEmailController {
//        return TestEmailController(InMemoryStatisticsNotifier());
//    }

//    @Bean
//    open fun emailStatisticsNotifier(/*javaMailSender: JavaMailSender*/): EmailStatisticsNotifier {
//        return EmailStatisticsNotifier(/*javaMailSender*/)
//    }
//
//    @Bean
//    open fun importStatistics(clock: ClockInterface): ImportStatistics {
//        return ImportStatistics(clock, InMemoryStatisticsNotifier())
//    }
}