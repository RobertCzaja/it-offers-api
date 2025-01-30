package pl.api.itoffers.report

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.mail.javamail.JavaMailSender

@Configuration
open class StatisticsNotifierConfig {

    @Bean
    open fun inMemoryStatisticsNotifier(): InMemoryStatisticsNotifier {
        return InMemoryStatisticsNotifier();
    }

    @Bean
    @Primary
    open fun emailStatisticsNotifier(mailSender: JavaMailSender): EmailStatisticsNotifier {
        return EmailStatisticsNotifier(mailSender);
    }

}