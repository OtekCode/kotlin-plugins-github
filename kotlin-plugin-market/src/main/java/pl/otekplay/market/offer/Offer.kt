package pl.otekplay.market.offer

import pl.otekplay.database.DatabaseEntity
import pl.otekplay.plugin.config.ConfigEnchantedItem
import pl.otekplay.plugin.config.ConfigItem
import java.util.*

data class Offer(
        val offerId: UUID,
        val offerOwner: UUID,
        val offeredItem: ConfigEnchantedItem,
        val needItems: Collection<ConfigItem>,
        val createTime: Long,
        val validTime: Long
): DatabaseEntity() {
    override val id: String
        get() = "offerId"
    override val key: String
        get() = offerId.toString()
    override val collection: String
        get() = "market_offers"
}