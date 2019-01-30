package pl.otekplay.market.creator

import org.bukkit.entity.Player
import pl.otekplay.market.MarketPlugin
import pl.otekplay.market.offer.PrepareOffer
import pl.otekplay.menu.api.Menu

class CreatorMenu(val plugin: MarketPlugin){
    fun openCreatorNeedItemMenu(player: Player, prepareOffer: PrepareOffer){
        plugin.creatorConfig.run {
           // val menu = Menu(optionsMenuCreator)
        }

    }

}