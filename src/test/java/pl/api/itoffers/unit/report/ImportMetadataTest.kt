package pl.api.itoffers.unit.report

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.api.itoffers.report.model.ImportMetadata
import pl.api.itoffers.report.model.TechnologyStats
import java.time.LocalDateTime
import java.util.*

class ImportMetadataTest {
    @Test
    fun `when it's finished returns statistics report for current import`() {

        val importMetadata = ImportMetadata(
            UUID.fromString("12994bf4-a862-4f3f-8458-df8c3c10d765"),
            LocalDateTime.of(2025,2,1,6,0,0,670000000),
            listOf("php", "java", "go", "devops").associateWith { TechnologyStats(it) }.toMutableMap(),
            "NO_FLUFF_JOBS"
        )

        repeat(20) { importMetadata.registerFetchedOffer("php") }
        repeat(20) { importMetadata.registerFetchedOffer("java") }
        repeat(20) { importMetadata.registerFetchedOffer("go") }
        repeat(20) { importMetadata.registerFetchedOffer("devops") }
        repeat(2) { importMetadata.registerNewOffer("java") }
        repeat(20) { importMetadata.registerNewOffer("devops") }
        val report = importMetadata.finish(LocalDateTime.of(2025,2,1,6,0,44,680000000))

        assertEquals(mapOf(
            "title" to "✅ NO_FLUFF_JOBS Import", // todo move to some static method and remove duplication
            "scrapingId" to "12994bf4-a862-4f3f-8458-df8c3c10d765",
            "day" to "2025-02-01",
            "from" to "06:00:00",
            "to" to "06:00:44",
            "duration" to "0m 44s",
            "technologies" to mapOf(
                "php" to mapOf("fetched" to 20, "new" to 0),
                "java" to mapOf("fetched" to 20, "new" to 2),
                "go" to mapOf("fetched" to 20, "new" to 0),
                "devops" to mapOf("fetched" to 20, "new" to 20),
            ),
        ),report)
    }
}