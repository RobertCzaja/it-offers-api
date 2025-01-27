package pl.api.itoffers.report

class TechnologyStats (
    val name: String
) {
    private var fetchedOffersCount: Int = 0;
    private var savedNewOffersCount: Int = 0;

    fun registerFetchedOffer() {
        fetchedOffersCount++;
    }

    fun registerNewOffer() {
        savedNewOffersCount++;
    }
}