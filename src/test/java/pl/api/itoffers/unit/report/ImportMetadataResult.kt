package pl.api.itoffers.unit.report

object ImportMetadataResult {
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
}