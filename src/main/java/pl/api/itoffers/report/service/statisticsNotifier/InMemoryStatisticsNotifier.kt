package pl.api.itoffers.report.service.statisticsNotifier

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import pl.api.itoffers.report.service.StatisticsNotifier

@Service
@Profile("test")
class InMemoryStatisticsNotifier : StatisticsNotifier {

    var report: String? = null
    var reportDetails: Map<String, Any>? = null

    @Deprecated("to remove")
    override fun notifyDeprecated(report: String) {
        this.report = report
    }

    override fun notify(reportDetails: Map<String, Any>) {
        this.reportDetails = reportDetails
    }
}