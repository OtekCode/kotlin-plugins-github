package pl.otekplay.market.owner

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import pl.otekplay.market.MarketPlugin

class OwnerListener(val plugin: MarketPlugin): Listener {


    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val manager = plugin.ownerManager
        val uniqueId = e.player.uniqueId
        if (manager.getOwner(uniqueId) != null) return
        manager.createOwner(uniqueId)
    }
}