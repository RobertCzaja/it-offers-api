package pl.api.itoffers.report.service.statisticsNotifier

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import pl.api.itoffers.report.service.StatisticsNotifier

@Service
@Profile("test")
class InMemoryStatisticsNotifier : StatisticsNotifier {
    var reportDetails: Map<String, Any>? = null

    override fun notify(reportDetails: Map<String, Any>) {
        this.reportDetails = reportDetails
    }
}