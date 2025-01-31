package pl.api.itoffers.report

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.mail.javamail.JavaMailSender
import pl.api.itoffers.shared.utils.clock.ClockInterface

@Configuration
open class StatisticsNotifierConfig {

    @Bean
    open fun inMemoryStatisticsNotifier(): InMemoryStatisticsNotifier {
        return InMemoryStatisticsNotifier()
    }

    @Bean
    @Primary
    open fun emailStatisticsNotifier(mailSender: JavaMailSender): EmailStatisticsNotifier {
        return EmailStatisticsNotifier(mailSender)
    }

    @Bean
    open fun statisticsNotifier(mailSender: JavaMailSender): StatisticsNotifier {
        return EmailStatisticsNotifier(mailSender)
    }

    @Bean
    open fun importStatistics(emailStatisticsNotifier: EmailStatisticsNotifier, clock: ClockInterface): ImportStatistics {
        return ImportStatistics(clock, emailStatisticsNotifier)
    }
}