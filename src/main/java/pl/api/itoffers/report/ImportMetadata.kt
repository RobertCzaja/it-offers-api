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