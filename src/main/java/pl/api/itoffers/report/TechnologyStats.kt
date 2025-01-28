package pl.api.itoffers.report

class TechnologyStats (
    val name: String
) {
    var fetchedOffersCount: Int = 0
        private set
    var savedNewOffersCount: Int = 0
        private set

    fun registerFetchedOffer() {
        fetchedOffersCount++;
    }

    fun registerNewOffer() {
        savedNewOffersCount++;
    }
}