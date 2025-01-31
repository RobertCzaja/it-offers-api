package pl.api.itoffers.report.service

interface StatisticsNotifier {
    fun notify(report: String)
}