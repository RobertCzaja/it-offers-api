package pl.api.itoffers.report

interface StatisticsNotifier {
    fun notify(report: String)
}