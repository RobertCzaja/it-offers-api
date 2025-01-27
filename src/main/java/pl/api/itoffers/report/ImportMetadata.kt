package pl.api.itoffers.report

import java.time.LocalDateTime
import java.util.*

class ImportMetadata (
    val startedAt: LocalDateTime,
    val technologiesStats: MutableMap<String, TechnologyStats>
) {
}