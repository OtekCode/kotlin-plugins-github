package pl.otekplay.menu.api

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class MenuListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked is Player && event.inventory.holder is MenuHolder) {
            event.isCancelled = true
            (event.inventory.holder as MenuHolder).menu.onInventoryClick(event)
        }
    }

}
