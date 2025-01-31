package pl.api.itoffers.unit.report;

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.api.itoffers.helper.FrozenClock
import pl.api.itoffers.report.service.ImportStatistics
import pl.api.itoffers.report.exception.ImportStatisticsException
import pl.api.itoffers.report.service.InMemoryStatisticsNotifier
import java.time.LocalDateTime
import java.util.*


class ImportStatisticsTest {

    private val scrapingId = UUID.fromString("10098463-49d1-4794-95ed-cf0cda286002")
    private val technologies = listOf("php","java","ruby")
    private val frozenClock = FrozenClock()
    private val inMemoryNotifier = InMemoryStatisticsNotifier()
    private val importStatistics = ImportStatistics(frozenClock, inMemoryNotifier)

    @Test
    fun `is able to generate report from gathered statistics`() {

        importStatistics.start(scrapingId, technologies)
        importStatistics.provider(scrapingId, "JUST_JOIN_IT")
        importStatistics.registerFetchedOffer(scrapingId, "php")
        importStatistics.registerFetchedOffer(scrapingId, "php")
        importStatistics.registerFetchedOffer(scrapingId, "php")
        importStatistics.registerFetchedOffer(scrapingId, "java")
        importStatistics.registerFetchedOffer(scrapingId, "java")
        importStatistics.registerFetchedOffer(scrapingId, "java")
        importStatistics.registerFetchedOffer(scrapingId, "java")
        importStatistics.registerNewOffer(scrapingId, "php")
        importStatistics.registerNewOffer(scrapingId, "php")
        importStatistics.registerNewOffer(scrapingId, "java")
        importStatistics.registerNewOffer(scrapingId, "java")
        importStatistics.registerNewOffer(scrapingId, "java")
        frozenClock.setNow(LocalDateTime.of(2025, 1, 10, 17, 31, 28))
        importStatistics.finish(scrapingId)
        importStatistics.start(scrapingId, technologies)

        assertEquals(
            "Scraping ID: 10098463-49d1-4794-95ed-cf0cda286002\n"+
                    "Provider: JUST_JOIN_IT\n"+
                    "Started at: 2025-01-10T17:28:05\n"+
                    "Finished at: 2025-01-10T17:31:28\n"+
                    "Duration: 3m 23s\n\n"+
                    "Technologies:\n"+
                    "php: fetched: 3, new: 2\n"+
                    "java: fetched: 4, new: 3\n"+
                    "ruby: fetched: 0, new: 0\n",
            inMemoryNotifier.report
        )
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
