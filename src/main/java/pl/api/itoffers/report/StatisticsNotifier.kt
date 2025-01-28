package pl.api.itoffers.report

interface StatisticsNotifier {
    fun info (message: String)

    fun notify(report: String)
}