package pl.api.itoffers.unit.report;

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import pl.api.itoffers.helper.FrozenClock
import pl.api.itoffers.report.ImportStatistics
import pl.api.itoffers.report.InMemoryStatisticsLogger
import java.util.UUID


class ImportStatisticsTest {

    private val importStatistics = ImportStatistics(FrozenClock(), InMemoryStatisticsLogger());

    @Test
    fun `is able to generate report from gathered statistics`() {

        val scrapingId = UUID.randomUUID()
        val technologies = listOf("php","java","ruby")

        importStatistics.start(scrapingId, technologies)
        importStatistics.provider(scrapingId, "JUST_JOIN_IT")

        assertEquals("a","a") // todo add real assertions
    }
}
