package pl.api.itoffers.report

import java.time.LocalDateTime
import java.util.*

class ImportMetadata (
    val startedAt: LocalDateTime,
    val technologiesStats: MutableMap<String, TechnologyStats>,
    var providerName: String? = null,
) {
    fun setProvider(providerName: String) {
        this.providerName = providerName;
    }

    fun finish(finishedAt: LocalDateTime): String {

        var report = buildString {
            append("Started at: $startedAt\n")
            append("Finished at: $finishedAt\n")
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