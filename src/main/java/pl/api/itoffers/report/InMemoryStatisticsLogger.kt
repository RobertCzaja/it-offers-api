package pl.api.itoffers.report

import org.springframework.stereotype.Service

@Service
class InMemoryStatisticsLogger : StatisticsLogger {
    override fun info(message: String) {
        //todo
    }
}