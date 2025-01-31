package pl.api.itoffers.report.service

import org.springframework.stereotype.Service

@Service
class InMemoryStatisticsNotifier : StatisticsNotifier {

    var report: String? = null

    override fun notify(report: String) {
        this.report = report
    }
}