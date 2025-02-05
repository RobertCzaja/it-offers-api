package pl.api.itoffers.report.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.api.itoffers.provider.FetchDetailsFailedEvent
import pl.api.itoffers.provider.FetchListFailedEvent
import pl.api.itoffers.provider.ImportFinishedEvent
import pl.api.itoffers.provider.ImportStartedEvent
import pl.api.itoffers.report.service.ImportStatistics

@Component
class ProviderListener (
    private val ImportStatistics : ImportStatistics,
) {
    @EventListener
    fun importStarted(event: ImportStartedEvent) {
    }

    @EventListener
    fun importFinished(event: ImportFinishedEvent) {
    }

    @EventListener
    fun fetchListFailedEvent(event: FetchListFailedEvent) {
    }

    @EventListener
    fun fetchDetailsFailedEvent(event: FetchDetailsFailedEvent) {
    }
}