package pl.api.itoffers.report.model

import pl.api.itoffers.report.exception.ImportStatisticsException
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ImportMetadata (
    private val scrapingId: UUID,
    private val startedAt: LocalDateTime,
    private val technologiesStats: MutableMap<String, TechnologyStats>,
    private var providerName: String? = null,
) {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val TIME_FORMAT = "HH:mm:ss"
    }

    @Deprecated("to remove - pass through constructor")
    fun setProvider(providerName: String) {
        this.providerName = providerName;
    }

    @Deprecated("to remove - use the new version")
    fun finishDeprecated(finishedAt: LocalDateTime): String {
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

    fun finish(finishedAt: LocalDateTime): Map<String, Any> {
        val duration = Duration.between(startedAt, finishedAt)

        val report = mapOf<String, Any>(
            "title" to "✅ $providerName Import",
            "scrapingId" to scrapingId.toString(),
            "day" to startedAt.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            "from" to startedAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
            "to" to finishedAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
            "duration" to "${duration.toMinutes()}m ${duration.toSeconds() % 60}s",
            "technologies" to technologiesStats.mapValues {
                mapOf("fetched" to it.value.fetchedOffersCount, "new" to it.value.savedNewOffersCount)
            }.toMutableMap(),
        )
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