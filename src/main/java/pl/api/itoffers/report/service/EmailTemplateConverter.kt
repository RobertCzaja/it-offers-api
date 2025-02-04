package pl.api.itoffers.report.service

import freemarker.template.Configuration
import org.springframework.stereotype.Service
import java.io.StringWriter

@Service
class EmailTemplateConverter (
    private val freemarkerConfig: Configuration
) {
    fun convert(data: Map<String, Any>): String {
        val template = freemarkerConfig.getTemplate("import-report.ftl")
        val writer = StringWriter()
        template.process(data, writer)
        return writer.toString()
    }
}