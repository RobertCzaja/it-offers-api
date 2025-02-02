package pl.api.itoffers.unit.report

import org.junit.Test
import org.junit.Assert.assertTrue
import pl.api.itoffers.report.factory.FreeMakerFactory
import java.io.StringWriter

class GenerateEmailHtmlTest {

    @Test
    fun `generates email content based on html template`() {
        val importData = mapOf(
            "title" to "✅ NO_FLUFF_JOBS Import",
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
        )

        val freemarkerConfig = FreeMakerFactory.create()
        val template = freemarkerConfig.getTemplate("import-report.ftl")
        val writer = StringWriter()
        template.process(importData, writer)
        val emailHtmlContent = writer.toString()

        assertTrue(emailHtmlContent.contains("✅ NO_FLUFF_JOBS Import"))
        assertTrue(emailHtmlContent.contains("12994bf4-a862-4f3f-8458-df8c3c10d765"))
        assertTrue(emailHtmlContent.contains("2025-02-01"))
        assertTrue(emailHtmlContent.contains("06:00:00"))
        assertTrue(emailHtmlContent.contains("06:00:44"))
        assertTrue(emailHtmlContent.contains("06:00:44"))
        assertTrue(emailHtmlContent.contains("php"))
        assertTrue(emailHtmlContent.contains("java"))
        assertTrue(emailHtmlContent.contains("go"))
        assertTrue(emailHtmlContent.contains("devops"))
    }
}