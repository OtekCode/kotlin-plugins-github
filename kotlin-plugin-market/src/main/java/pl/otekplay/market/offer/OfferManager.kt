package pl.otekplay.market.offer

import org.bukkit.entity.Player
import pl.otekplay.database.DatabaseAPI
import pl.otekplay.market.MarketPlugin
import java.util.*

class OfferManager(val plugin: MarketPlugin) {
    private val offers = HashMap<UUID, Offer>()

    init {
        DatabaseAPI.loadAll<Offer>("market_offers") {
            it.forEach { offers[it.offerId] = it }
            plugin.logger.info("Loaded ${offers.size} market offers.")
        }
    }


    fun addOffer(player: Player,prepareOffer: PrepareOffer){
        val id = UUID.randomUUID()
        val offer = Offer(
                id,
                player.uniqueId,
                prepareOffer.item,
                prepareOffer.neededItems,
                System.currentTimeMillis(),
                prepareOffer.buyTime!!.timeItem
        )
        offer.insertEntity()
    }



}