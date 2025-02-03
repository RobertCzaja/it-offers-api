package pl.api.itoffers.unit.report

import org.junit.Test
import org.junit.Assert.assertTrue
import pl.api.itoffers.report.factory.FreeMakerFactory
import java.io.StringWriter

class GenerateEmailHtmlTest {

    @Test
    fun `generates email content based on html template`() {
        val freemarkerConfig = FreeMakerFactory.create()
        val template = freemarkerConfig.getTemplate("import-report.ftl")
        val writer = StringWriter()
        template.process(ImportMetadataResult.getMap(), writer)
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