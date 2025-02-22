package pl.api.itoffers.report.service

import org.springframework.stereotype.Service
import pl.api.itoffers.report.exception.ImportStatisticsException
import pl.api.itoffers.report.model.TechnologyStats
import pl.api.itoffers.report.model.ImportMetadata
import pl.api.itoffers.report.model.TechnologyError
import pl.api.itoffers.shared.utils.clock.ClockInterface
import java.util.UUID

@Service
open class ImportStatistics (
    private val clock: ClockInterface,
    private val log: StatisticsNotifier
) {
    private val metadata: MutableMap<UUID, ImportMetadata> = mutableMapOf()

    fun start(scrapingId: UUID, technologies: List<String>, providerName: String) {
        importIsNotInitialized(scrapingId)
        val technologiesStats: MutableMap<String, TechnologyStats> = mutableMapOf()
        technologies.forEach { technology ->
            technologiesStats.put(technology, TechnologyStats(technology))
        }

        metadata[scrapingId] = ImportMetadata(scrapingId, clock.now(), technologiesStats, providerName)
    }

    fun finish(scrapingId: UUID) {
        importIsInitialized(scrapingId)
        val report = metadata[scrapingId]?.finish(clock.now())
        if (null != report) {
            log.notify(report)
        }
        metadata.remove(scrapingId)
    }

    fun registerError(scrapingId: UUID, technology: String, exceptionClass: String, exceptionMessage: String) {
        importIsInitialized(scrapingId)
        metadata[scrapingId]?.registerError(technology, TechnologyError(exceptionClass, exceptionMessage));
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