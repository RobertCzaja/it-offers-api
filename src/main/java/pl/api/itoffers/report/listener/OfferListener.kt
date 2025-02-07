package pl.api.itoffers.report.listener

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.api.itoffers.offer.application.event.NewOfferAddedEvent
import pl.api.itoffers.offer.application.event.OfferSavingFailedEvent
import pl.api.itoffers.report.service.ImportStatistics

@Component
class OfferListener (
    private val ImportStatistics : ImportStatistics
) {
    @EventListener
    fun newOfferAddedEvent(event: NewOfferAddedEvent) {
    }

    @EventListener
    fun offerSavingFailedEvent(event: OfferSavingFailedEvent) {

    }
}