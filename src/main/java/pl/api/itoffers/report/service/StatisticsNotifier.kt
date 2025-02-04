package pl.api.itoffers.report.service

interface StatisticsNotifier {
    fun notify(reportDetails: Map<String, Any>);
}