package pl.otekplay.market.offer

enum class OfferFilters(
        val comparator: java.util.Comparator<Offer>
) {
    TIME(OfferFilters.comparatorByTime),
    AUTHOR(OfferFilters.comparatorByAuthor),
    TYPE(OfferFilters.comparatorByType);

    companion object {
        private val comparatorByTime = Comparator<Offer> { o1, o2 -> o1.createTime.compareTo(o2.createTime) }
        private val comparatorByAuthor = Comparator<Offer> { o1, o2 -> o1.offerOwner.compareTo(o2.offerOwner) }
        private val comparatorByType = Comparator<Offer> { o1, o2 -> o1.offeredItem.id.compareTo(o2.offeredItem.id) }
    }
}