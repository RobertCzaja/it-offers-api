package pl.api.itoffers.report.model

class TechnologyStats (
    val name: String
) {
    var fetchedOffersCount: Int = 0
        private set
    var savedNewOffersCount: Int = 0
        private set
    var errors: MutableList<TechnologyError> = mutableListOf()
        private set

    fun registerError(error: TechnologyError) {
        errors.add(error)
    }

    fun registerFetchedOffer() {
        fetchedOffersCount++
    }

    fun registerNewOffer() {
        savedNewOffersCount++
    }
}