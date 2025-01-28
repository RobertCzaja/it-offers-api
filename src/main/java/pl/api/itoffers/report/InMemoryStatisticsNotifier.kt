package pl.api.itoffers.report

import org.springframework.stereotype.Service

@Service
class InMemoryStatisticsNotifier : StatisticsNotifier {

    var report: String? = null

    override fun info(message: String) {
        //todo
    }

    override fun notify(report: String) {
        this.report = report
    }
}