package pl.otekplay.market.owner

import pl.otekplay.database.DatabaseEntity
import pl.otekplay.plugin.config.ConfigEnchantedItem
import java.util.*

class Owner(
        val uniqueId: UUID
): DatabaseEntity() {
    override val id: String
        get() = "uniqueId"
    override val key: String
        get() = uniqueId.toString()
    override val collection: String
        get() = "market_owners"
    val items = arrayListOf<ConfigEnchantedItem>()
}