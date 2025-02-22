package pl.api.itoffers.report.service

fun interface StatisticsNotifier {
    fun notify(reportDetails: Map<String, Any>);
}