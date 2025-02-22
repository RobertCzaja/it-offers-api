package pl.api.itoffers.report.listener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.api.itoffers.provider.*
import pl.api.itoffers.report.service.ImportStatistics

@Component
class ProviderListener (
    private val importStatistics : ImportStatistics,
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @EventListener
    fun importStarted(event: ImportStartedEvent) {
        importStatistics.start(event.scrapingId, event.technologies, event.providerName)
    }

    @EventListener
    fun importFinished(event: ImportFinishedEvent) {
        importStatistics.finish(event.scrapingId)
    }

    @EventListener
    fun fetchListFailedEvent(event: FetchListFailedEvent) {
        log.error("Error on fetching list of {}: {}", event.technology, event.e.message)
        importStatistics.registerError(
            event.scrapingId,
            event.technology,
            event.e.javaClass.canonicalName,
            event.e.message.toString(),
        )
    }

    @EventListener
    fun fetchDetailsFailedEvent(event: FetchDetailsFailedEvent) {
        //wip
    }

    @EventListener
    fun offerFetched(event: OfferFetchedEvent) {
        importStatistics.registerFetchedOffer(event.scrapingId, event.technology)
    }
}