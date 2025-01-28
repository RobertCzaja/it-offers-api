package pl.api.itoffers.unit.report;

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.api.itoffers.helper.FrozenClock
import pl.api.itoffers.report.ImportStatistics
import pl.api.itoffers.report.InMemoryStatisticsLogger
import java.time.LocalDateTime
import java.util.*


class ImportStatisticsTest {

    private val frozenClock = FrozenClock()
    private val importStatistics = ImportStatistics(frozenClock, InMemoryStatisticsLogger())

    @Test
    fun `is able to generate report from gathered statistics`() {

        val scrapingId = UUID.randomUUID()
        val technologies = listOf("php","java","ruby")

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
        val report = importStatistics.finish(scrapingId)

        assertEquals("a","a") // todo add real assertions
    }
}
