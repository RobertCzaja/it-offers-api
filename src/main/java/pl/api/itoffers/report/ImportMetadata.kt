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
}