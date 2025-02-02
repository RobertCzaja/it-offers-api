package pl.api.itoffers.report.factory

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler

object FreeMakerFactory {
    fun create(): Configuration {
        return Configuration(Configuration.VERSION_2_3_32).apply {
            setClassForTemplateLoading(this@FreeMakerFactory.javaClass, "/templates/")
            defaultEncoding = "UTF-8"
            templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
        }
    }
}