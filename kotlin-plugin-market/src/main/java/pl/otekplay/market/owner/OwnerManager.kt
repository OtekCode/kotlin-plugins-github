package pl.otekplay.market.owner

import pl.otekplay.database.DatabaseAPI
import pl.otekplay.market.MarketPlugin
import java.util.*

class OwnerManager(val plugin: MarketPlugin){
    private val owners = HashMap<UUID,Owner>()


    init {
        DatabaseAPI.loadAll<Owner>("market_owners") {
            it.forEach { owners[it.uniqueId] = it }
            plugin.logger.info("Loaded ${owners.size} market offers.")
        }
    }
    fun createOwner(uniqueId:UUID){
        val owner = Owner(uniqueId)
        owners[uniqueId] = owner
        owner.insertEntity()
    }

    fun getOwner(uniqueId: UUID) = owners[uniqueId]
}