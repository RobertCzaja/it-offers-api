package pl.api.itoffers.report.service

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("test")
class InMemoryStatisticsNotifier : StatisticsNotifier {

    var report: String? = null

    override fun notify(report: String) {
        this.report = report
    }
}