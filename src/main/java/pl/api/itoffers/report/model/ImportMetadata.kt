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
    private var providerName: String,
) {
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
        const val TIME_FORMAT = "HH:mm:ss"
    }

    fun finish(finishedAt: LocalDateTime): Map<String, Any> {
        val duration = Duration.between(startedAt, finishedAt)

        val report = mapOf<String, Any>(
            "title" to "✅ $providerName Import",
            "hasErrors" to hasErrors(),
            "scrapingId" to scrapingId.toString(),
            "day" to startedAt.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            "from" to startedAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
            "to" to finishedAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT)),
            "duration" to "${duration.toMinutes()}m ${duration.toSeconds() % 60}s",
            "technologies" to technologiesStats.mapValues {
                mapOf(
                    "fetched" to it.value.fetchedOffersCount,
                    "new" to it.value.savedNewOffersCount,
                    "errors" to it.value.errors.map { error -> mapOf(
                        "class" to error.exceptionClass,
                        "message" to error.exceptionMessage,
                    ) },
                )
            }.toMutableMap(),
        )
        return report
    }

    fun registerError(technology: String, technologyError: TechnologyError) {
        technologyExists(technology)
        technologiesStats[technology]?.registerError(technologyError)
    }

    fun registerFetchedOffer(technology: String) {
        technologyExists(technology)
        technologiesStats[technology]?.registerFetchedOffer()
    }

    fun registerNewOffer(technology: String) {
        technologyExists(technology)
        technologiesStats[technology]?.registerNewOffer()
    }

    private fun hasErrors(): Boolean {
        for (technologyStat in technologiesStats) {
            if (technologyStat.value.errors.size > 0) {
                return true
            }
        }
        return false
    }

    private fun technologyExists(technology: String) {
        if (!technologiesStats.containsKey(technology)) {
            throw ImportStatisticsException.technologyDoesNotExist(technology)
        }
    }
}