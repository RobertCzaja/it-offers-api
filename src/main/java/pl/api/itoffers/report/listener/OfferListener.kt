package pl.api.itoffers.report.listener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.api.itoffers.offer.application.event.NewOfferAddedEvent
import pl.api.itoffers.offer.application.event.OfferSavingFailedEvent
import pl.api.itoffers.report.service.ImportStatistics

@Component
class OfferListener (
    private val importStatistics : ImportStatistics
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    @EventListener
    fun newOfferAddedEvent(event: NewOfferAddedEvent) {
        log.info("[{}] {}", event.technology, event.title)
        importStatistics.registerNewOffer(event.scrapingId, event.technology)
    }

    @EventListener
    fun offerSavingFailedEvent(event: OfferSavingFailedEvent) {
        log.error(
            "Error on saving new Offer to DB, Exception: {} with message: {}",
            event.e.javaClass.canonicalName,
            event.e.message
        )
        importStatistics.registerError(
            event.scrapingId,
            event.technology,
            event.e.javaClass.canonicalName,
            event.e.message.toString(),
        )
    }
}