package pl.api.itoffers.report.model

import pl.api.itoffers.report.exception.ImportStatisticsException
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class ImportMetadata (
    private val scrapingId: UUID,
    private val startedAt: LocalDateTime,
    private val technologiesStats: MutableMap<String, TechnologyStats>,
    private var providerName: String? = null,
) {
    fun setProvider(providerName: String) {
        this.providerName = providerName;
    }

    fun finish(finishedAt: LocalDateTime): String {
        val duration = Duration.between(startedAt, finishedAt)
        var report = buildString {
            append("Scraping ID: $scrapingId\n")
            append("Provider: $providerName\n")
            append("Started at: $startedAt\n")
            append("Finished at: $finishedAt\n")
            append("Duration: ${duration.toMinutes()}m ${duration.toSeconds() % 60}s\n")
            append("\n")
            append("Technologies:\n")
        }

        for ((technology, stats) in technologiesStats) {
            report += "$technology: fetched: ${stats.fetchedOffersCount}, new: ${stats.savedNewOffersCount}\n"
        }

        return report
    }

    fun registerFetchedOffer(technology: String) {
        technologyExists(technology)
        technologiesStats[technology]?.registerFetchedOffer()
    }

    fun registerNewOffer(technology: String) {
        technologyExists(technology)
        technologiesStats[technology]?.registerNewOffer()
    }

    private fun technologyExists(technology: String) {
        if (!technologiesStats.containsKey(technology)) {
            throw ImportStatisticsException.technologyDoesNotExist(technology)
        }
    }
}