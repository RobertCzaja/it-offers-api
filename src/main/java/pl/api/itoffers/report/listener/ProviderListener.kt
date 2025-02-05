package pl.api.itoffers.report.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.api.itoffers.provider.ImportStartedEvent
import pl.api.itoffers.report.service.ImportStatistics

@Component
class ProviderListener (
    private val ImportStatistics : ImportStatistics,
) {

    @EventListener
    fun importStarted(event: ImportStartedEvent) {
    }
}