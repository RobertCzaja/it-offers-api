package pl.api.itoffers.unit.report;

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.api.itoffers.helper.FrozenClock
import pl.api.itoffers.report.service.ImportStatistics
import pl.api.itoffers.report.exception.ImportStatisticsException
import pl.api.itoffers.report.service.statisticsNotifier.InMemoryStatisticsNotifier
import java.time.LocalDateTime
import java.util.*


class ImportStatisticsTest {

    private val scrapingId = UUID.fromString("12994bf4-a862-4f3f-8458-df8c3c10d765")
    private val technologies = listOf("php","java","go", "devops")
    private val frozenClock = FrozenClock()
    private val inMemoryNotifier = InMemoryStatisticsNotifier()
    private val importStatistics = ImportStatistics(frozenClock, inMemoryNotifier)

    @Test
    fun `is able to generate report from gathered statistics`() {

        frozenClock.setNow(LocalDateTime.of(2025,2,1,6,0,0,680000000))
        importStatistics.start(scrapingId, technologies)
        importStatistics.provider(scrapingId, "NO_FLUFF_JOBS")
        repeat(20) { importStatistics.registerFetchedOffer(scrapingId, "php") }
        repeat(20) { importStatistics.registerFetchedOffer(scrapingId, "java") }
        repeat(20) { importStatistics.registerFetchedOffer(scrapingId, "go") }
        repeat(20) { importStatistics.registerFetchedOffer(scrapingId, "devops") }
        repeat(2) { importStatistics.registerNewOffer(scrapingId, "java") }
        repeat(20) { importStatistics.registerNewOffer(scrapingId, "devops") }
        frozenClock.setNow(LocalDateTime.of(2025,2,1,6,0,44,680000000))
        importStatistics.finish(scrapingId)
        importStatistics.start(scrapingId, technologies)

        assertEquals(ImportMetadataResult.getMap(), inMemoryNotifier.reportDetails)
    }

    @Test
    fun `cannot create one import twice`() {
        importStatistics.start(scrapingId, technologies)
        val exception = assertThrows<ImportStatisticsException> {
            importStatistics.start(scrapingId, technologies)
        }

        assertEquals("Import $scrapingId already initialized", exception.message)
    }

    @Test
    fun `cannot finish import when it has not started`() {
        val exception = assertThrows<ImportStatisticsException> {
            importStatistics.finish(scrapingId)
        }

        assertEquals("Import $scrapingId not initialized", exception.message)
    }

    @Test
    fun `cannot set provider when import is not initialized`() {
        val exception = assertThrows<ImportStatisticsException> {
            importStatistics.provider(scrapingId, "JUST_JOIN_IT")
        }

        assertEquals("Import $scrapingId not initialized", exception.message)
    }

    @Test
    fun `cannot register fetched offer when import is not initialized`() {
        val exception = assertThrows<ImportStatisticsException> {
            importStatistics.registerFetchedOffer(scrapingId, "php")
        }

        assertEquals("Import $scrapingId not initialized", exception.message)
    }

    @Test
    fun `cannot register new offer when import is not initialized`() {
        val exception = assertThrows<ImportStatisticsException> {
            importStatistics.registerNewOffer(scrapingId, "php")
        }

        assertEquals("Import $scrapingId not initialized", exception.message)
    }
}
