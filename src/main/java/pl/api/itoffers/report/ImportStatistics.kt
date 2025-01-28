package pl.api.itoffers.report

import org.springframework.stereotype.Service
import pl.api.itoffers.shared.utils.clock.ClockInterface
import java.util.UUID

@Service
class ImportStatistics (
    val clock: ClockInterface,
    val log: StatisticsLogger
) {
    private val metadata: MutableMap<UUID, ImportMetadata> = mutableMapOf()

    fun start(scrapingId: UUID, technologies: List<String>) {
        importIsNotInitialized(scrapingId)
        val technologiesStats: MutableMap<String, TechnologyStats> = mutableMapOf()
        technologies.forEach { technology ->
            technologiesStats.put(technology, TechnologyStats(technology))
        }

        metadata[scrapingId] = ImportMetadata(clock.now(), technologiesStats)
        log.info("Import started: $scrapingId")
    }

    fun finish(scrapingId: UUID) {
        importIsInitialized(scrapingId)
        val report = metadata[scrapingId]?.finish(clock.now())
        metadata.remove(scrapingId)

        // todo log/send email
    }

    fun provider(scrapingId: UUID, providerName: String) {
        importIsInitialized(scrapingId)
        metadata[scrapingId]?.setProvider(providerName)
    }

    fun registerFetchedOffer(scrapingId: UUID, technology: String) {
        importIsInitialized(scrapingId)
        metadata[scrapingId]?.registerFetchedOffer(technology);
    }

    fun registerNewOffer(scrapingId: UUID, technology: String) {
        importIsInitialized(scrapingId)
        metadata[scrapingId]?.registerNewOffer(technology);
    }

    private fun importIsInitialized(scrapingId: UUID) {
        if (!metadata.containsKey(scrapingId)) {
            throw ImportStatisticsException.importIsNotInitialized(scrapingId)
        }
    }

    private fun importIsNotInitialized(scrapingId: UUID) {
        if (metadata.containsKey(scrapingId)) {
            throw ImportStatisticsException.importIsAlreadyInitialized(scrapingId)
        }
    }
}