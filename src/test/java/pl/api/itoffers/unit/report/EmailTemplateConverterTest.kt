package pl.api.itoffers.unit.report

import org.junit.Test
import org.junit.Assert.assertTrue
import pl.api.itoffers.report.factory.FreeMakerFactory
import pl.api.itoffers.report.service.EmailTemplateConverter

class EmailTemplateConverterTest {

    @Test
    fun `generates email content based on html template`() {
        val emailHtmlContent = EmailTemplateConverter(FreeMakerFactory.create()).convert(ImportMetadataResult.getMapWithErrors())

        assertTrue(emailHtmlContent.contains("12994bf4-a862-4f3f-8458-df8c3c10d765"))
        assertTrue(emailHtmlContent.contains("2025-02-01"))
        assertTrue(emailHtmlContent.contains("06:00:00"))
        assertTrue(emailHtmlContent.contains("06:00:44"))
        assertTrue(emailHtmlContent.contains("06:00:44"))
        assertTrue(emailHtmlContent.contains("php"))
        assertTrue(emailHtmlContent.contains("java"))
        assertTrue(emailHtmlContent.contains("go"))
        assertTrue(emailHtmlContent.contains("devops"))
        assertTrue(emailHtmlContent.contains("RuntimeException"))
        assertTrue(emailHtmlContent.contains("Error1"))
        assertTrue(emailHtmlContent.contains("Error2"))
    }
}