package pl.otekplay.inventorylimits

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerPickupItemEvent

class InventoryLimitListener(private val plugin: InventoryLimitPlugin) : Listener {

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        if (plugin.manager.getDeposit(e.player.uniqueId) == null) plugin.manager.createDeposit(e.player.uniqueId)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClose(e: InventoryCloseEvent) {
        plugin.manager.checkInventoryPlayer(e.player as Player)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerPickupItem(e: PlayerPickupItemEvent) {
        plugin.manager.checkInventoryPlayer(e.player)
    }

}