package pl.api.itoffers.report;

import java.util.UUID

class ImportStatisticsException(message: String) : Exception(message) {
    companion object {
        fun importIsNotInitialized(scrapingId: UUID): ImportStatisticsException {
            return ImportStatisticsException("Import $scrapingId not initialized")
        }
        fun importIsAlreadyInitialized(scrapingId: UUID): ImportStatisticsException {
            return ImportStatisticsException("Import $scrapingId already initialized")
        }
    }
}
