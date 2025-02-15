package pl.api.itoffers.unit.report

import pl.api.itoffers.report.model.TechnologyError

object ImportMetadataResult {
    @Deprecated("to remove")
    fun getMap(): Map<String, Any> {
        return mapOf(
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
    }

    fun getMapWithErrors(): Map<String, Any> {
        return mapOf(
            "title" to "✅ NO_FLUFF_JOBS Import",
            "scrapingId" to "12994bf4-a862-4f3f-8458-df8c3c10d765",
            "day" to "2025-02-01",
            "from" to "06:00:00",
            "to" to "06:00:44",
            "duration" to "0m 44s",
            "technologies" to mapOf(
                "php" to mapOf(
                    "fetched" to 20,
                    "new" to 0,
                    "errors" to listOf<Map<String, String>>(),
                ),
                "java" to mapOf(
                    "fetched" to 20,
                    "new" to 2,
                    "errors" to listOf(
                        mapOf("class" to "RuntimeException", "message" to "Error1"),
                        mapOf("class" to "RuntimeException", "message" to "Error2"),
                    ),
                ),
                "go" to mapOf(
                    "fetched" to 20,
                    "new" to 0,
                    "errors" to listOf<Map<String, String>>(),
                ),
                "devops" to mapOf(
                    "fetched" to 20,
                    "new" to 20,
                    "errors" to listOf<Map<String, String>>(),
                ),
            ),
        )
    }
}