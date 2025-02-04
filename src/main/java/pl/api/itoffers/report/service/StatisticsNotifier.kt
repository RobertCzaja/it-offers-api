package pl.api.itoffers.report.service

interface StatisticsNotifier {
    @Deprecated("to remove")
    fun notifyDeprecated(report: String)

    fun notify(reportDetails: Map<String, Any>);
}