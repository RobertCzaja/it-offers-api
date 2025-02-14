package pl.api.itoffers.report.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.api.itoffers.provider.FetchDetailsFailedEvent
import pl.api.itoffers.provider.FetchListFailedEvent
import pl.api.itoffers.provider.ImportFinishedEvent
import pl.api.itoffers.provider.ImportStartedEvent
import pl.api.itoffers.provider.OfferFetchedEvent
import pl.api.itoffers.report.service.ImportStatistics

@Component
class ProviderListener (
    private val importStatistics : ImportStatistics,
) {
    @EventListener
    fun importStarted(event: ImportStartedEvent) {
        importStatistics.start(event.scrapingId, event.technologies) // todo merge into one
        importStatistics.provider(event.scrapingId, event.providerName)
    }

    @EventListener
    fun importFinished(event: ImportFinishedEvent) {
        importStatistics.finish(event.scrapingId)
    }

    @EventListener
    fun fetchListFailedEvent(event: FetchListFailedEvent) {
        // todo
    }

    @EventListener
    fun fetchDetailsFailedEvent(event: FetchDetailsFailedEvent) {
        // todo
    }

    @EventListener
    fun offerFetched(event: OfferFetchedEvent) {
        importStatistics.registerFetchedOffer(event.scrapingId, event.technology)
    }
}